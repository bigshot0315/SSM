package com.xudaning.config;

/**
 * 创建用户：杨辽
 * 创建时间：2016-11-04 17:30:00
 * 描    述：
 */

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Locale;

/**
 * 实现Web层的日志切面
 *
 * @author 菩提树下的椰子
 * <p>
 * 实现AOP的切面主要有以下几个要素：
 * 使用@Aspect注解将一个java类定义为切面类
 * 使用@Pointcut定义一个切入点，可以是一个规则表达式，比如下例中某个package下的所有函数，也可以是一个注解等。
 * 根据需要在切入点不同位置的切入内容
 * 使用@Before在切入点开始处切入内容
 * 使用@After在切入点结尾处切入内容
 * 使用@AfterReturning在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
 * 使用@Around在切入点前后切入内容，并自己控制何时执行切入点自身的内容
 * 使用@AfterThrowing用来处理当切入内容部分抛出异常之后的处理逻辑
 * <p>
 * AOP切面中的同步问题
 * 在WebLogAspect切面中，分别通过doBefore和doAfterReturning两个独立函数实现了切点头部和切点返回后执行的内容，
 * 若我们想统计请求的处理时间，就需要在doBefore处记录时间，并在doAfterReturning处通过当前时间与开始处记录的时间计
 * 算得到请求处理的消耗时间。
 * 那么我们是否可以在WebLogAspect切面中定义一个成员变量来给doBefore和doAfterReturning一起访问呢？是否会有同步
 * 问题呢？的确，直接在这里定义基本类型会有同步问题，所以我们可以引入ThreadLocal对象，像下面这样进行记录：
 * <p>
 * AOP切面的优先级
 * 由于通过AOP实现，程序得到了很好的解耦，但是也会带来一些问题，比如：我们可能会对Web层做多个切面，校验用户，校验头信息
 * 等等，这个时候经常会碰到切面的处理顺序问题。
 * 所以，我们需要定义每个切面的优先级，我们需要@Order(i)注解来标识切面的优先级。i的值越小，优先级越高。假设我们还有一
 * 个切面是CheckNameAspect用来校验name必须为didi，我们为其设置@Order(10)，而上文中WebLogAspect设置为
 * @Order(5)，所以WebLogAspect有更高的优先级，这个时候执行顺序是这样的： --在@Before中优先执行@Order(5)的内容，再执行@Order(10)的内容
 * --在@After和@AfterReturning中优先执行@Order(10)的内容，再执行@Order(5)的内容
 * 所以我们可以这样子总结：
 * --在切入点前的操作，按order的值由小到大执行
 * --在切入点后的操作，按order的值由大到小执行
 */
@Aspect            //使用@Aspect注解将一个java类定义为切面类
@Order(5)
@Configuration
public class WebLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    //使用@Pointcut定义一个切入点，可以是一个规则表达式，比如下例中某个package下的所有函数，也可以是一个注解等。
    @Pointcut("execution(public * com.xudaning.controller..*.*(..))")
    public void webLog() {
    }


    @Resource
    private HttpServletResponse response;

    //使用@Before在切入点开始处切入内容
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        LOGGER.info("请求URL : " + request.getRequestURL().toString());
        LOGGER.info("请求HTTP_METHOD : " + request.getMethod());
        LOGGER.info("请求IP : " + request.getRemoteAddr());
        LOGGER.info("请求CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        LOGGER.info("请求参数值 : " + Arrays.toString(joinPoint.getArgs()));

        Cookie[] cookies = request.getCookies();
        if (null == cookies) {
            return;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("language")) {
                String lang = cookie.getValue();
                LOGGER.info("language cookies---->>>{}" + lang);
                LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
                if ("zh".equals(lang)) {
                    localeResolver.setLocale(request, response, new Locale("zh", "CN"));
                } else if ("en".equals(lang)) {
                    localeResolver.setLocale(request, response, new Locale("en", "US"));
                } else if ("id".equals(lang)) {
                    localeResolver.setLocale(request, response, new Locale("en", "ID"));
                } else if ("br".equals(lang)) {
                    localeResolver.setLocale(request, response, new Locale("en", "BR"));
                }
            }
        }
    }


    //使用@AfterReturning在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        LOGGER.info("响应RESPONSE : " + ret);
        LOGGER.info("响应时间SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
    }

}
