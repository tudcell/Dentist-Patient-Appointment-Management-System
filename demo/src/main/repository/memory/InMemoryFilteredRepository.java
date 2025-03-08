package main.repository.memory;

import main.domain.Identifiable;
import main.filters.Filter;
import main.repository.IRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryFilteredRepository<ID, T extends Identifiable<ID>> extends InMemoryRepository<ID, T> {
    private final IRepository<ID, T> repository;
    private final Filter<T> filter;

    public InMemoryFilteredRepository(IRepository<ID, T> repository, Filter<T> filter) {
        this.repository = repository;
        this.filter = filter;
    }

    @Override
    public Iterable<T> findAll() {
        List<T> filteredItems = new ArrayList<>();

        for (T item : this.repository.findAll()) {
            if (filter.accept(item)) {
                filteredItems.add(item);
            }
        }

        return filteredItems;
    }
}