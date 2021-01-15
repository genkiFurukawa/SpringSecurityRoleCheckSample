# README
## やりたいこと
Spring Securityの機能を使ってAPI実行時にROLEのチェックを行いたい

## 実現方法
WebSecurityConfigureAdapterを継承して@EnableWebSecurityをつけたJavaConfigのクラスを作成する
JavaConfigに`@EnableGlobalMethodSecurity(securedEnabled = true)`をつけることで、Controllerで`@Secured`を使ってROLEチェックを行うことができる

```java
@RestController
public class MyController {

    @GetMapping(value = "/hello")
    @Secured({"ROLE_TEST"})
    public String hello() {
        return "hello";
    }
}
```

Controllerの共通処理でSecurityContextにロールを付与することで、ロールのチェックを行うことができる。

```java
@Component
public class CustomHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Security ContextにROLEの情報を入れる
        // 本来はDBなどから権限があるか確認して入れるが、動作確認のため、決め打ちする
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_TEST"));

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("", "", authorityList));

        return true;
    }
}
```

## メモ
* Spring Securityのキーとなる要素
    1. Security Filter Chain
        * 認証、URLパスの認可、CSRF防止などの役割
    1. Security Context
        * IDや権限を格納するオブジェクト
        * staticメソッドでプログラムの任意の箇所で参照できる
            * SecurityContextHandler.getContext()
* WebSecurityConfigureAdapterを継承して@EnableWebSecurityをつけたJavaConfigのクラスを作成する
    * `@EnableGlobalMethodSecurity(securedEnabled = true)`をつけることでControllerで`@Secured`を使ってROLEチェックを行うことができる
* Spring Securityを導入するとレスポンスヘッダに情報が付与される

    ```
    $ curl -I http://localhost:8080/hello
    HTTP/1.1 200
    X-Content-Type-Options: nosniff
    X-XSS-Protection: 1; mode=block
    Cache-Control: no-cache, no-store, max-age=0, must-revalidate
    Pragma: no-cache
    Expires: 0
    X-Frame-Options: DENY
    Content-Type: text/plain;charset=UTF-8
    Content-Length: 5
    Date: Fri, 15 Jan 2021 15:32:38 GMT
    ```

## 参考
* [B5 今こそ知りたいSpring Security - Spring Fest2020](https://www.youtube.com/watch?v=o_opWd9cZ10&feature=youtu.be)
