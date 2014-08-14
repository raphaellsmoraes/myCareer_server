package infra.specification;

/**
 * @author Claudio Eduardo de Oliveira (claudioeduardo.deoliveira@sonymobile.com)
 */
public interface Specification<T> {

    boolean isSatisfiedBy(T instance);

}
