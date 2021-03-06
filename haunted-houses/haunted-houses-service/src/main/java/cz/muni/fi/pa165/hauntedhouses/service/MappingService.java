package cz.muni.fi.pa165.hauntedhouses.service;

import java.util.Collection;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

/**
 * @author Jan Horvath
 */

@Service
public interface MappingService {

    public  <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    public  <T> T mapTo(Object u, Class<T> mapToClass);

    public Mapper getMapper();
}
