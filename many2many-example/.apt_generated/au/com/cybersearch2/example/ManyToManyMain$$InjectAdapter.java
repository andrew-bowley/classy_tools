// Code generated by dagger-compiler.  Do not edit.
package au.com.cybersearch2.example;

import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

/**
 * A {@code Binding<ManyToManyMain>} implementation which satisfies
 * Dagger's infrastructure requirements including:
 *
 * Owning the dependency links between {@code ManyToManyMain} and its
 * dependencies.
 *
 * Being a {@code Provider<ManyToManyMain>} and handling creation and
 * preparation of object instances.
 *
 * Being a {@code MembersInjector<ManyToManyMain>} and handling injection
 * of annotated fields.
 */
public final class ManyToManyMain$$InjectAdapter extends Binding<ManyToManyMain>
    implements Provider<ManyToManyMain>, MembersInjector<ManyToManyMain> {
  private Binding<au.com.cybersearch2.classyjpa.persist.PersistenceFactory> persistenceFactory;

  public ManyToManyMain$$InjectAdapter() {
    super("au.com.cybersearch2.example.ManyToManyMain", "members/au.com.cybersearch2.example.ManyToManyMain", NOT_SINGLETON, ManyToManyMain.class);
  }

  /**
   * Used internally to link bindings/providers together at run time
   * according to their dependency graph.
   */
  @Override
  @SuppressWarnings("unchecked")
  public void attach(Linker linker) {
    persistenceFactory = (Binding<au.com.cybersearch2.classyjpa.persist.PersistenceFactory>) linker.requestBinding("au.com.cybersearch2.classyjpa.persist.PersistenceFactory", ManyToManyMain.class, getClass().getClassLoader());
  }

  /**
   * Used internally obtain dependency information, such as for cyclical
   * graph detection.
   */
  @Override
  public void getDependencies(Set<Binding<?>> getBindings, Set<Binding<?>> injectMembersBindings) {
    injectMembersBindings.add(persistenceFactory);
  }

  /**
   * Returns the fully provisioned instance satisfying the contract for
   * {@code Provider<ManyToManyMain>}.
   */
  @Override
  public ManyToManyMain get() {
    ManyToManyMain result = new ManyToManyMain();
    injectMembers(result);
    return result;
  }

  /**
   * Injects any {@code @Inject} annotated fields in the given instance,
   * satisfying the contract for {@code Provider<ManyToManyMain>}.
   */
  @Override
  public void injectMembers(ManyToManyMain object) {
    object.persistenceFactory = persistenceFactory.get();
  }

}
