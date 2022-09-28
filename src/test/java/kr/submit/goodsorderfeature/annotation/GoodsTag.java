package kr.submit.goodsorderfeature.annotation;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.*;

@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Tag("GOODS")
public @interface GoodsTag {
}