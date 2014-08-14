package infra.specification;

/**
 * @author Claudio Eduardo de Oliveira (claudioeduardo.deoliveira@sonymobile.com)
 */
public class NotSpecification<T> extends AbstractSpecification<T> {

    private Specification<T> specification1;

    public NotSpecification(final Specification<T> specification1) {
        super();
        this.specification1 = specification1;
    }

    @Override
    public boolean isSatisfiedBy(T instance) {
        return !specification1.isSatisfiedBy(instance);
    }

}
