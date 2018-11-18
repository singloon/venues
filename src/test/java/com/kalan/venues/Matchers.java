package com.kalan.venues;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import java.util.function.Function;

public class Matchers {
    public static <F,U> FeatureMatcher<F,U> feature(Function<F, U> feature, Matcher<U> expected) {
        return feature(feature, expected, "");
    }

    public static <F,U> FeatureMatcher<F,U> feature(Function<F, U> feature, Matcher<U> expected, String description) {
        return feature(feature, expected, description, description);
    }

    public static <F,U> FeatureMatcher<F,U> feature(Function<F, U> feature, Matcher<U> expected, String description, String featureName) {
        return new FeatureMatcher<F, U>(expected, description, featureName) {
            @Override
            protected U featureValueOf(F from) {
                return feature.apply(from);
            }
        };
    }
}
