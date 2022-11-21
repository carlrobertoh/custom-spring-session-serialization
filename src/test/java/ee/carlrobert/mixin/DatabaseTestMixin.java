package ee.carlrobert.mixin;

import static ee.carlrobert.mixin.DatabaseTestMixin.Private.jdbcTemplate;
import static ee.carlrobert.mixin.DatabaseTestMixin.Private.transactionTemplate;

import java.util.function.Consumer;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public interface DatabaseTestMixin {

  class Private {
    static JdbcTemplate jdbcTemplate;
    static TransactionTemplate transactionTemplate;
  }

  @Autowired
  default void setDependencies(JdbcTemplate jdbcTemplate, TransactionTemplate transactionTemplate) {
    Private.jdbcTemplate = jdbcTemplate;
    Private.transactionTemplate = transactionTemplate;
  }

  default void cleanupDatabase(String... tableNames) {
    executeSql(jdbcTemplate -> jdbcTemplate.batchUpdate(
        Stream.of(tableNames)
            .map(name -> "DELETE FROM " + name)
            .toArray(String[]::new)
    ));
  }

  default void executeSql(Consumer<JdbcTemplate> templateSupplier) {
    runInTransaction(() -> templateSupplier.accept(jdbcTemplate));
  }

  default void runInTransaction(Runnable runnable) {
    transactionTemplate.execute((TransactionCallback<Void>) status -> {
      runnable.run();
      return null;
    });
  }
}
