package site.yan.local.advice;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AnnotatedMethodTracedAdvice extends AbstractMethodTracedAdvice {
    public AnnotatedMethodTracedAdvice() {
        super();
    }

    @Pointcut("@annotation(site.yan.local.annotation.Traced)")
    public void pointcut() {
    }
}
