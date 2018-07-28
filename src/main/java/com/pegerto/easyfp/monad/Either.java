package com.pegerto.easyfp.monad;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Either represents a value of one of two possible types, ( a disjoig union)
 * A common use of Either is as an alternative to Option for dealing with possible missing values.
 *
 * @param <L> Left value
 * @param <R> Right value
 */
public class Either<L, R> {

  private final Optional<L> left;
  private final Optional<R> right;

  private Either(Optional<L> left, Optional<R> right) {
    this.left = left;
    this.right = right;
  }

  public static <L, R> Either<L, R> left(L left) {
    return new Either(Optional.of(left), Optional.empty());
  }

  public static <L, R> Either<L, R> right(R right) {
    return new Either(Optional.empty(), Optional.of(right));
  }

  public L left() {
    return left.get();
  }

  public R right() {
    return right.get();
  }

  public <T> T map(Function<? super L, ? extends T> lFunc, Function<? super R, ? extends T> rFunc) {
    return left.<T>map(lFunc)
        .orElseGet(() -> right.map(rFunc).get());
  }

  public <T> Either<T, R> mapLeft(Function<? super L, ? extends T> lFunc) {
    return new Either<>(left.map(lFunc), right);
  }

  public <T> Either<L, T> mapRight(Function<? super R, ? extends T> rFunc) {
    return new Either<>(left, right.map(rFunc));
  }

  public void apply(Consumer<? super L> lFunc, Consumer<? super R> rFunc) {
    left.ifPresent(lFunc);
    right.ifPresent(rFunc);
  }
}
