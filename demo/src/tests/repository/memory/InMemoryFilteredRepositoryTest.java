package tests.repository.memory;

import main.filters.FilterPatientsByName;
import main.repository.memory.InMemoryFilteredRepository;
import org.junit.jupiter.api.Test;
import tests.repository.FakeRepository;

public class InMemoryFilteredRepositoryTest {
    public FilterPatientsByName<Integer> filterByName = new FilterPatientsByName<>("John Doe");

    @Test
    public void test_findAll_emptyRepository_throwsException(){
        FakeRepository fakeRepository = new FakeRepository();
        fakeRepository.findAllShouldThrowException = true;
        InMemoryFilteredRepository filteredRepository = new InMemoryFilteredRepository<>(fakeRepository, filterByName);
        try{
            filteredRepository.findAll();
            assert false;
        }catch (Exception e){}
    }
}