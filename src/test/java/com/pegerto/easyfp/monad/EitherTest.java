package com.pegerto.easyfp.monad;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import org.junit.jupiter.api.Test;

public class EitherTest {

  private final Object LEFT_EXPECTED = mock(Object.class);
  private final Object RIGHT_EXPECTED = mock(Object.class);
  private final Function TEST_F1 = x -> LEFT_EXPECTED;
  private final Function TEST_F2 = x -> RIGHT_EXPECTED;
  private final Object LEFT = mock(Object.class);
  private final Object RIGHT = mock(Object.class);

  @Test public void testLeft() {
    Either either = Either.left(LEFT);
    Throwable thrown = catchThrowable(() -> either.right());

    assertThat(either.left()).isEqualTo(LEFT);
    assertThat(thrown).isInstanceOf(NoSuchElementException.class);
  }

  @Test public void testRight() {
    Either either = Either.right(RIGHT);
    Throwable thrown = catchThrowable(() -> either.left());

    assertThat(either.right()).isEqualTo(RIGHT);
    assertThat(thrown).isInstanceOf(NoSuchElementException.class);
  }

  @Test public void testMapLeft() {
    Object left = mock(Object.class);
    Either either = Either.left(LEFT);
    Object result = either.map(TEST_F1, TEST_F2);

    assertThat(result).isEqualTo(LEFT_EXPECTED);
  }

  @Test public void testMapRight() {
    Either either = Either.right(RIGHT);
    Object result = either.map(TEST_F1, TEST_F2);

    assertThat(result).isEqualTo(RIGHT_EXPECTED);
  }

  @Test public void testMapLeftLeft() {
    Either either = Either.left(LEFT);
    Either result = either.mapLeft(TEST_F1);
    assertThat(result.left()).isEqualTo(LEFT_EXPECTED);
  }

  @Test public void testMapLeftRight() {
    Either either = Either.right(RIGHT);

    Either result = either.mapLeft(TEST_F1);
    Throwable thrown = catchThrowable(() -> either.left());

    assertThat(thrown).isInstanceOf(NoSuchElementException.class);
    assertThat(result.right()).isEqualTo(RIGHT);
  }

  @Test public void testMapRightRight() {
    Either either = Either.right(RIGHT);
    Either result = either.mapRight(TEST_F2);
    assertThat(result.right()).isEqualTo(RIGHT_EXPECTED);
  }

  @Test public void testMapRightLeft() {
    Either either = Either.left(LEFT);

    Either result = either.mapRight(TEST_F2);
    Throwable thrown = catchThrowable(() -> either.right());

    assertThat(thrown).isInstanceOf(NoSuchElementException.class);
    assertThat(result.left()).isEqualTo(LEFT);
  }

  @Test public void testApplyLeft() {
    Either either = Either.left(LEFT);
    Consumer<Object> consumer = mock(Consumer.class);

    either.apply(consumer, consumer);
    verify(consumer).accept(LEFT);
  }

  @Test public void testApplyRight() {
    Either either = Either.right(RIGHT);
    Consumer<Object> consumer = mock(Consumer.class);

    either.apply(consumer, consumer);
    verify(consumer).accept(RIGHT);
  }

}
