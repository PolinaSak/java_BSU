package catalog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class App 
{
    public static void main(String[] args) {
        Catalog catalog = new Catalog("Мастер и Маргарита", "М. Булгаков");

        catalog.issueBook("Бобиков", LocalDate.of(2026, 9, 1));
        catalog.returnBook(LocalDate.of(2026, 9, 15));

        catalog.issueBook("Судин", LocalDate.of(2026, 9, 20));
        catalog.returnBook(LocalDate.of(2026, 10, 5));

        catalog.issueBook("Патрицкий", LocalDate.of(2026, 10, 10));
        catalog.returnBook(LocalDate.of(2026, 10, 30));

        catalog.issueBook("Плебеев", LocalDate.of(2026, 11, 1)); // не возвращена

        System.out.println("Вся история выдач");
        for (Catalog.Lending l : catalog.getHistory()) {
            System.out.println(l);
        }

        List<Catalog.Lending> bobikov = catalog.find(new Catalog.LendingFilter() {
            @Override
            public boolean accept(Catalog.Lending lending) {
                return lending.getReader().equals("Бобиков");
            }
        });
        System.out.println("\nВыдачи читателю Бобиков");
        bobikov.forEach(System.out::println);

        List<Catalog.Lending> notReturned = catalog.find(new Catalog.LendingFilter() {
            @Override
            public boolean accept(Catalog.Lending lending) {
                return !lending.isReturned();
            }
        });
        System.out.println("\nКнига на руках");
        notReturned.forEach(System.out::println);

        List<Catalog.Lending> sorted = new ArrayList<>(catalog.getHistory());
        sorted.sort(new Comparator<Catalog.Lending>() {
            @Override
            public int compare(Catalog.Lending a, Catalog.Lending b) {
                return a.getReader().compareTo(b.getReader());
            }
        });
        System.out.println("\nСортировка по читателю");
        sorted.forEach(System.out::println);

        System.out.println("\nКнига доступна: " + catalog.isAvailable());
    }
}