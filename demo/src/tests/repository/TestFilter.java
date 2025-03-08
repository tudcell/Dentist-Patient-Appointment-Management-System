package tests.repository;

import main.filters.Filter;

public class TestFilter implements Filter<TestEntity> {
    private String nameFilter;

    public TestFilter(String nameFilter) {
        this.nameFilter = nameFilter;
    }

    @Override
    public boolean accept(TestEntity item) {
        return item.getName().equalsIgnoreCase(nameFilter);
    }
}
