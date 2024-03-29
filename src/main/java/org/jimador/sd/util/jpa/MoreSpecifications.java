package org.jimador.sd.util.jpa;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

/**
 * Utility class to construct {@link Specification} objects
 *
 * @author James Dunnam
 * @see Specification
 * @see org.springframework.data.jpa.domain.Specifications
 */

public class MoreSpecifications<T> implements Specification<T> {

    /**
     * Build a <em>"in"</em> {@link Specification}
     *
     * @param expressionExtractor {@code Function} to extract the {@link Expression}
     * @param coll                the collection of values for the <em>"in"</em>
     * @param <E>                 the entity type
     * @param <X>                 the collection type
     *
     * @return A {@link Specification} wrapping a {@link ValueInSpecification}
     */
    public static <E, X> Specification<E> valueIn(Function<Root<E>, Expression<X>> expressionExtractor, Collection<X> coll) {
        return Specification.where(new ValueInSpecification<>(expressionExtractor, coll));
    }

    /**
     * Build a <em>"equals"</em> {@link Specification}
     *
     * @param expressionExtractor {@code Function} to extract the {@link Expression}
     * @param value               the value for the <em>"equals"</em>
     * @param <E>                 the entity type
     * @param <V>                 the value type
     *
     * @return A {@link Specification} wrapping a {@link EqualsSpecification}
     */
    public static <E, V> Specification<E> equalTo(Function<Root<E>, Expression<V>> expressionExtractor, V value) {
        return Specification.where(new EqualsSpecification<>(expressionExtractor, value));
    }

    /**
     * Build a case-sensitive <em>"like"</em> {@link Specification}
     *
     * @param expressionExtractor {@code Function} to extract the {@link Expression}
     * @param value               the {@code String} for the <em>"like"</em>
     * @param <E>                 the entity type
     *
     * @return A {@link MoreSpecifications} wrapping a {@link LikeSpecification}
     */
    public static <E> Specification<E> like(Function<Root<E>, Expression<String>> expressionExtractor, String value) {
        return Specification.where(new LikeSpecification<>(expressionExtractor, value == null ? null : "%" + value + "%", false));
    }

    /**
     * Build a case-insensitive <em>"like"</em> {@link Specification}
     *
     * @param expressionExtractor {@code Function} to extract the {@link Expression}
     * @param value               the {@code String} for the <em>"like"</em>
     * @param <E>                 the entity type
     *
     * @return A {@link MoreSpecifications} wrapping a {@link LikeSpecification}
     */
    public static <E> Specification<E> likeIC(Function<Root<E>, Expression<String>> expressionExtractor, String value) {
        return Specification.where(new LikeSpecification<>(expressionExtractor, value == null ? null : "%" + value + "%", true));
    }

    /**
     * Build a case-sensitive <em>"starts with"</em> {@link Specification}
     *
     * @param expressionExtractor {@code Function} to extract the {@link Expression}
     * @param value               the {@code String} for the <em>"starts with"</em>
     * @param <E>                 the entity type
     *
     * @return A {@link Specification} wrapping a {@link LikeSpecification}
     *
     */
    public static <E> Specification<E> startsWith(Function<Root<E>, Expression<String>> expressionExtractor, String value) {
        return Specification.where(new LikeSpecification<>(expressionExtractor, value == null ? null : value + "%", false));
    }

    /**
     * Build a case-insensitive <em>"starts with"</em> {@link Specification}
     *
     * @param expressionExtractor {@code Function} to extract the {@link Expression}
     * @param value               the {@code String} for the <em>"starts with"</em>
     * @param <E>                 the entity type
     *
     * @return A {@link Specification} wrapping a {@link LikeSpecification}
     */
    public static <E> Specification<E> startsWithIC(Function<Root<E>, Expression<String>> expressionExtractor, String value) {
        return Specification.where(new LikeSpecification<>(expressionExtractor, value == null ? null : value + "%", true));
    }

    /**
     * Build a case-sensitive <em>"ends with"</em> {@link Specification}
     *
     * @param expressionExtractor {@code Function} to extract the {@link Expression}
     * @param value               the {@code String} for the <em>"ends with"</em>
     * @param <E>                 the entity type
     *
     * @return A {@link Specification} wrapping a {@link LikeSpecification}
     *
     */
    public static <E> Specification<E> endsWith(Function<Root<E>, Expression<String>> expressionExtractor, String value) {
        return Specification.where(new LikeSpecification<>(expressionExtractor, value == null ? null : "%" + value, false));
    }

    /**
     * Build a case-insensitive <em>"ends with"</em> {@link Specification}
     *
     * @param expressionExtractor {@code Function} to extract the {@link Expression}
     * @param value               the {@code String} for the <em>"ends with"</em>
     * @param <E>                 the entity type
     *
     * @return A {@link Specification} wrapping a {@link LikeSpecification}
     */
    public static <E> Specification<E> endsWithIC(Function<Root<E>, Expression<String>> expressionExtractor, String value) {
        return Specification.where(new LikeSpecification<>(expressionExtractor, value == null ? null : "%" + value, true));
    }


    /**
     * Build a <em>"distinct"</em> {@link Specification}
     *
     * @param specification the specification to wrap
     *
     * @return A {@link Specification} wrapping a {@link DistinctSpecification}
     */
    public static <E> Specification<E> distinct(Specification<E> specification) {
        return Specification.where(new DistinctSpecification<>(specification));
    }


    /**
     * Specification that wraps {@link CriteriaBuilder#equal(Expression, Object)}
     *
     * @param <E> the entity type
     * @param <V> the value type
     */
    private static class EqualsSpecification<E, V> implements Specification<E> {
        private final Function<Root<E>, Expression<V>> expressionExtractor;
        private final V value;

        EqualsSpecification(Function<Root<E>, Expression<V>> expressionExtractor, V value) {
            Objects.requireNonNull(expressionExtractor, "Expression extractor must not be null");
            Objects.requireNonNull(value, "Value must not be null");
            this.expressionExtractor = expressionExtractor;
            this.value = value;
        }

        /*
         * (non-Javadoc)
         * @see org.springframework.data.jpa.domain.Specification#toPredicate
         */
        @Override
        public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            return cb.equal(expressionExtractor.apply(root), value);
        }
    }

    /**
     * Specification that wraps {@link CriteriaBuilder#in(Expression)}
     *
     * @param <E> the Entity type
     * @param <X> the collection type
     */
    private static class ValueInSpecification<E, X> implements Specification<E> {
        private final Function<Root<E>, Expression<X>> expressionExtractor;
        private final Collection<X> coll;

        ValueInSpecification(Function<Root<E>, Expression<X>> expressionExtractor, Collection<X> coll) {
            Objects.requireNonNull(expressionExtractor, "Expression extractor must not be null");
            Objects.requireNonNull(coll, "Collection must not be null");
            this.expressionExtractor = expressionExtractor;
            this.coll = coll;
        }

        /*
         * (non-Javadoc)
         * @see org.springframework.data.jpa.domain.Specification#toPredicate
         */
        @Override
        public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            final CriteriaBuilder.In<X> in = cb.in(expressionExtractor.apply(root));
            for (X x : coll) {
                in.value(x);
            }
            return in;
        }
    }

    /**
     * Specification that wraps {@link CriteriaBuilder#like(Expression, String)}
     *
     * @param <E> the entity type
     */
    private static class LikeSpecification<E> implements Specification<E> {

        private final Function<Root<E>, Expression<String>> expressionExtractor;
        private final String value;
        private final boolean ignoreCase;

        LikeSpecification(Function<Root<E>, Expression<String>> expressionExtractor, String value, boolean ignoreCase) {
            Objects.requireNonNull(expressionExtractor, "Expression extractor must not be null");
            Objects.requireNonNull(value, "Value must not be null");
            this.expressionExtractor = expressionExtractor;
            this.value = value;
            this.ignoreCase = ignoreCase;
        }

        /*
         * (non-Javadoc)
         * @see org.springframework.data.jpa.domain.Specification#toPredicate
         */
        @Override
        public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            final Expression<String> exp = expressionExtractor.apply(root);

            if (ignoreCase) {
                return cb.like(cb.upper(exp), value.toUpperCase());
            }

            return cb.like(exp, value);
        }
    }

    /**
     * Specification that wraps {@link CriteriaQuery#distinct(boolean)}
     *
     * @param <E> the entity type
     */
    private static class DistinctSpecification<E> implements Specification<E> {

        private final Specification<E> delegate;

        DistinctSpecification(Specification<E> delegate) {
            Objects.requireNonNull(delegate, "Specification must not be null");
            this.delegate = delegate;
        }

        /*
         * (non-Javadoc)
         * @see org.springframework.data.jpa.domain.Specification#toPredicate
         */
        @Override
        public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            query.distinct(true);
            return delegate.toPredicate(root, query, cb);
        }
    }

    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
