package infra.specification;

/**
 * @author Claudio Eduardo de Oliveira (claudioeduardo.deoliveira@sonymobile.com)
 */
public class AndSpecification<T> extends AbstractSpecification<T> {

    private Specification<T> specification1;

    private Specification<T> specification2;

    public AndSpecification(final Specification<T> specification1,
                            final Specification<T> specification2) {
        this.specification1 = specification1;
        this.specification2 = specification2;
    }

    @Override
    public boolean isSatisfiedBy(T instance) {
        return specification1.isSatisfiedBy(instance)
                && specification2.isSatisfiedBy(instance);
    }


}
