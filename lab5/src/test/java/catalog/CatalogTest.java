package catalog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CatalogTest {

    private Catalog catalog;

    @BeforeEach
    void setUp() {
        catalog = new Catalog("Мастер и Маргарита", "М. Булгаков");
    }

    @Test
    void newCatalogIsAvailableAndHistoryIsEmpty() {
        assertTrue(catalog.isAvailable());
        assertTrue(catalog.getHistory().isEmpty());
    }

    @Test
    void issueBookCreatesLendingWithCorrectData() {
        LocalDate date = LocalDate.of(2026, 9, 1);

        Catalog.Lending lending = catalog.issueBook("Бобиков", date);

        assertEquals(1, lending.getId());
        assertEquals("Бобиков", lending.getReader());
        assertEquals(date, lending.getIssueDate());
        assertNull(lending.getReturnDate());
        assertFalse(lending.isReturned());
        assertFalse(catalog.isAvailable());
        assertEquals(1, catalog.getHistory().size());
    }

    @Test
    void idsAreAssignedSequentially() {
        Catalog.Lending first = catalog.issueBook("Бобиков", LocalDate.of(2026, 9, 1));
        catalog.returnBook(LocalDate.of(2026, 9, 15));
        Catalog.Lending second = catalog.issueBook("Судин", LocalDate.of(2026, 9, 20));

        assertEquals(1, first.getId());
        assertEquals(2, second.getId());
    }

    @Test
    void returnBookClosesLastLending() {
        Catalog.Lending lending = catalog.issueBook("Бобиков", LocalDate.of(2026, 9, 1));
        LocalDate back = LocalDate.of(2026, 9, 15);

        catalog.returnBook(back);

        assertTrue(lending.isReturned());
        assertEquals(back, lending.getReturnDate());
        assertTrue(catalog.isAvailable());
    }

    @Test
    void cannotIssueBookThatIsAlreadyIssued() {
        catalog.issueBook("Бобиков", LocalDate.of(2026, 9, 1));

        assertThrows(IllegalStateException.class,
                () -> catalog.issueBook("Судин", LocalDate.of(2026, 9, 2)));
        assertEquals(1, catalog.getHistory().size());
    }

    @Test
    void cannotReturnBookThatWasNotIssued() {
        assertThrows(IllegalStateException.class,
                () -> catalog.returnBook(LocalDate.of(2026, 9, 1)));
    }

    @Test
    void cannotReturnBookTwice() {
        catalog.issueBook("Бобиков", LocalDate.of(2026, 9, 1));
        catalog.returnBook(LocalDate.of(2026, 9, 15));

        assertThrows(IllegalStateException.class,
                () -> catalog.returnBook(LocalDate.of(2026, 9, 16)));
    }

    @Test
    void returnDateBeforeIssueDateIsRejected() {
        catalog.issueBook("Бобиков", LocalDate.of(2026, 9, 10));

        assertThrows(IllegalArgumentException.class,
                () -> catalog.returnBook(LocalDate.of(2026, 9, 1)));
        assertFalse(catalog.isAvailable()); // выдача осталась открытой
    }

    @Test
    void historyIsReadOnly() {
        catalog.issueBook("Бобиков", LocalDate.of(2026, 9, 1));

        List<Catalog.Lending> history = catalog.getHistory();

        assertThrows(UnsupportedOperationException.class, () -> history.clear());
    }

    @Test
    void lendingToStringContainsOuterClassData() {
        Catalog.Lending lending = catalog.issueBook("Бобиков", LocalDate.of(2026, 9, 1));

        String text = lending.toString();

        assertTrue(text.contains("Мастер и Маргарита"));
        assertTrue(text.contains("М. Булгаков"));
        assertTrue(text.contains("Бобиков"));
        assertTrue(text.contains("ещё нет"));
    }

    @Test
    void findWorksWithAnonymousClassFilter() {
        fillHistory();

        List<Catalog.Lending> result = catalog.find(new Catalog.LendingFilter() {
            @Override
            public boolean accept(Catalog.Lending lending) {
                return lending.getReader().equals("Бобиков");
            }
        });

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(l -> l.getReader().equals("Бобиков")));
    }

    @Test
    void findNotReturnedUsingAnonymousClass() {
        fillHistory();

        List<Catalog.Lending> result = catalog.find(new Catalog.LendingFilter() {
            @Override
            public boolean accept(Catalog.Lending lending) {
                return !lending.isReturned();
            }
        });

        assertEquals(1, result.size());
        assertEquals("Плебеев", result.get(0).getReader());
    }

    @Test
    void findReturnsEmptyListWhenNothingMatches() {
        fillHistory();

        List<Catalog.Lending> result = catalog.find(new Catalog.LendingFilter() {
            @Override
            public boolean accept(Catalog.Lending lending) {
                return lending.getReader().equals("Никто");
            }
        });

        assertTrue(result.isEmpty());
    }

    /** Бобиков (дважды), Судин, затем Плебеев с невозвращённой книгой. */
    private void fillHistory() {
        catalog.issueBook("Бобиков", LocalDate.of(2026, 9, 1));
        catalog.returnBook(LocalDate.of(2026, 9, 15));
        catalog.issueBook("Судин", LocalDate.of(2026, 9, 20));
        catalog.returnBook(LocalDate.of(2026, 10, 5));
        catalog.issueBook("Бобиков", LocalDate.of(2026, 10, 10));
        catalog.returnBook(LocalDate.of(2026, 10, 30));
        catalog.issueBook("Плебеев", LocalDate.of(2026, 11, 1));
    }
}