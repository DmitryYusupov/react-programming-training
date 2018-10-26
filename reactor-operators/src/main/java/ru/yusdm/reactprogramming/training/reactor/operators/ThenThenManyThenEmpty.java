package ru.yusdm.reactprogramming.training.reactor.operators;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * All the thenXXX methods on Mono have one semantic in common: they ignore the source onNext
 * signals and react on completion signals (onComplete and onError),
 * continuing the sequence at this point with various options.
 * As a consequence, this can change the generic type of the returned Mono:
 * <p>
 * then will just replay the source terminal signal,
 * resulting in a Mono<Void> to indicate that this never signals any onNext.
 * <p>
 * thenEmpty not only returns a Mono<Void>, but it takes a Mono<Void> as a parameter.
 * It represents a concatenation of the source completion signal then the second, empty Mono completion signal.
 * In other words, it completes when A then B have both completed sequentially, and doesn't emit data.
 * <p>
 * thenMany waits for the source to complete then plays all the signals from its Publisher<R> parameter,
 * resulting in a Flux<R> that will "pause" until the source completes,
 * then emit the many elements from the provided publisher before replaying its completion signal as well.
 * <p>
 * <p>
 * <p>
 * then, thenMany, and thenEmpty operators,
 * which complete when the upper stream completes. The operators ignore incoming
 * elements and only replay completion or error signals. These operators can be useful for
 * triggering new streams as soon as the upper stream finishes processing
 */
public class ThenThenManyThenEmpty {

    public static void main(String args[]) {
        testThenMany();
    }

    private static void testThen() {

    }

    /**
     *  this.posts
     *             .deleteAll()
     *             .thenMany(
     *                 Flux
     *                     .just("Post one", "Post two")
     *                     .flatMap(
     *                         title -> this.posts.save(Post.builder().title(title).content("content of " + title).build())
     *                     )
     *             )
     *             .log()
     *             .subscribe(
     *                 null,
     *                 null,
     *                 () -> log.info("done initialization...")
     *             );
     */


    /**
     *  @Override
     *     public void onApplicationEvent(ApplicationReadyEvent event) {
     *         repository
     *             .deleteAll() //4
     *             .thenMany(
     *                 Flux
     *                     .just("A", "B", "C", "D") //5
     *                     .map(name -> new Profile(UUID.randomUUID().toString(), name + "@email.com")) //6
     *                     .flatMap(repository::save)  //7
     *             )
     *             .thenMany(repository.findAll()) //8
     *             .subscribe(profile -> log.info("saving " + profile.toString())); //9
     *     }
     *
     *
     * 4 - here we start a reactive pipeline by first deleting everything in the database.
     *     This operation returns a Mono<T>.
     *     Both Mono<T> and Flux<T> support chaining processing with the thenMany(Publisher<T>) method.
     *     So, after the deleteAll() method completes, we then want to process the writes of new data to the database.
     *
     * 5 - we use Reactor’s Flux<T>.just(T…​) factory method to create a new Publisher with a static list
     *     of String records, in-memory…​
     *
     * 6 - …​and we transform each record in turn into a Profile object…​
     * 7 - …​that we then persist to the database using our repository
     *
     * 8 - after all the data has been written to the database, we want to fetch all the records
     *     from the database to confirm what we have there
     * 9 - if we’d stopped at the previous line, the save operation, and run this program then we would see…​ nothing!
     *     Publisher<T> instances are lazy — you need to subscribe() to them to trigger their execution.
     *     This last line is where the rubber meets the road.
     *     In this case, we’re using the subscribe(Consumer<T>) variant that lets us visit every record returned
     *     from the repository.findAll() operation and print out the record
     */


    /**
     * Similarly, the then prefix now consistently indicates that the onNext of the source are to be discarded, instead building up on the terminal signals. This has been made consistent in both Flux and Mono:
     *
     * --- then() returns a Mono<Void> that only propagates the onComplete or onError signal from the source.
     *
     * --- then(Mono<V>) returns a Mono<V>: it waits for the original onComplete signal before switching
     *     to another provided Mono, emitting only the elements from that other Mono.
     *
     * --- thenMany(Publisher<V>) is similar, except it continues into a Flux<V>.
     *
     * --- thenEmpty(Publisher<Void>) returns a Mono<Void> that completes once the original
     *     Mono then the Publisher have completed. That is, it represents sequential completion,
     *     unlike and which subscribes to both sequences immediately.
     *
     *    Note that variants that were taking a Supplier parameter have been removed altogether
     *   (their lazy semantics can be replaced by a Mono.defer).
     *    Also, the Mono#thenEmpty described above was renamed from then(Publisher<Void>).
     */
    private static void testThenMany() {
        Flux.just(1, 2, 3)
                .thenMany(Mono.just(4))
                .subscribe(e -> System.out.println("On next " + e));
    }

    private static void testThenEmpty() {

    }
}
