package catalog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Catalog {
    private final String title;
    private final String author;
    private final List<Lending> history = new ArrayList<>();
    private int nextId = 1;

    public Catalog(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public interface LendingFilter {
        boolean accept(Lending lending);
    }

    public class Lending {
        private final int id;
        private final String reader;
        private final LocalDate issueDate;
        private LocalDate returnDate;

        private Lending(String reader, LocalDate issueDate) {
            this.id = nextId++;
            this.reader = reader;
            this.issueDate = issueDate;
        }

        public int getId() {
            return id;
        }

        public String getReader() {
            return reader;
        }

        public LocalDate getIssueDate() {
            return issueDate;
        }

        public LocalDate getReturnDate() {
            return returnDate;
        }

        public boolean isReturned() {
            return returnDate != null;
        }

        @Override
        public String toString() {
            return "#" + id + "\" " + Catalog.this.title + "\" (" + Catalog.this.author + ")"
                    + ", читатель: " + reader
                    + ", выдана: " + issueDate
                    + ", возвращена: " + (isReturned() ? returnDate : "ещё нет");
        }
    }

    public Lending issueBook(String reader, LocalDate date) {
        if (!isAvailable()) {
            throw new IllegalStateException("Книга \"" + title + "\" уже выдана читателю "
                    + history.get(history.size() - 1).getReader());
        }
        Lending lending = new Lending(reader, date);
        history.add(lending);
        return lending;
    }

    public void returnBook(LocalDate date) {
        if (isAvailable()) {
            throw new IllegalStateException("Книга \"" + title + "\" сейчас не выдана");
        }
        Lending last = history.get(history.size() - 1);
        if (date.isBefore(last.issueDate)) {
            throw new IllegalArgumentException("Дата возврата раньше даты выдачи");
        }
        last.returnDate = date;
    }

    public boolean isAvailable() {
        return history.isEmpty() || history.get(history.size() - 1).isReturned();
    }

    public List<Lending> getHistory() {
        return Collections.unmodifiableList(history);
    }

    public List<Lending> find(LendingFilter filter) {
        List<Lending> result = new ArrayList<>();
        for (Lending l : history) {
            if (filter.accept(l)) {
                result.add(l);
            }
        }
        return result;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}