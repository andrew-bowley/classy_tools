// Code generated by dagger-compiler.  Do not edit.
package au.com.cybersearch2.classyjpa;

import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

/**
 * A {@code Binding<AndroidJpaIntegrationTest>} implementation which satisfies
 * Dagger's infrastructure requirements including:
 *
 * Owning the dependency links between {@code AndroidJpaIntegrationTest} and its
 * dependencies.
 *
 * Being a {@code Provider<AndroidJpaIntegrationTest>} and handling creation and
 * preparation of object instances.
 *
 * Being a {@code MembersInjector<AndroidJpaIntegrationTest>} and handling injection
 * of annotated fields.
 */
public final class AndroidJpaIntegrationTest$$InjectAdapter extends Binding<AndroidJpaIntegrationTest>
    implements Provider<AndroidJpaIntegrationTest>, MembersInjector<AndroidJpaIntegrationTest> {
  private Binding<au.com.cybersearch2.classyjpa.persist.PersistenceFactory> persistenceFactory;
  private Binding<JpaIntegrationTest> supertype;

  public AndroidJpaIntegrationTest$$InjectAdapter() {
    super("au.com.cybersearch2.classyjpa.AndroidJpaIntegrationTest", "members/au.com.cybersearch2.classyjpa.AndroidJpaIntegrationTest", NOT_SINGLETON, AndroidJpaIntegrationTest.class);
  }

  /**
   * Used internally to link bindings/providers together at run time
   * according to their dependency graph.
   */
  @Override
  @SuppressWarnings("unchecked")
  public void attach(Linker linker) {
    persistenceFactory = (Binding<au.com.cybersearch2.classyjpa.persist.PersistenceFactory>) linker.requestBinding("au.com.cybersearch2.classyjpa.persist.PersistenceFactory", AndroidJpaIntegrationTest.class, getClass().getClassLoader());
    supertype = (Binding<JpaIntegrationTest>) linker.requestBinding("members/au.com.cybersearch2.classyjpa.JpaIntegrationTest", AndroidJpaIntegrationTest.class, getClass().getClassLoader(), false, true);
  }

  /**
   * Used internally obtain dependency information, such as for cyclical
   * graph detection.
   */
  @Override
  public void getDependencies(Set<Binding<?>> getBindings, Set<Binding<?>> injectMembersBindings) {
    injectMembersBindings.add(persistenceFactory);
    injectMembersBindings.add(supertype);
  }

  /**
   * Returns the fully provisioned instance satisfying the contract for
   * {@code Provider<AndroidJpaIntegrationTest>}.
   */
  @Override
  public AndroidJpaIntegrationTest get() {
    AndroidJpaIntegrationTest result = new AndroidJpaIntegrationTest();
    injectMembers(result);
    return result;
  }

  /**
   * Injects any {@code @Inject} annotated fields in the given instance,
   * satisfying the contract for {@code Provider<AndroidJpaIntegrationTest>}.
   */
  @Override
  public void injectMembers(AndroidJpaIntegrationTest object) {
    object.persistenceFactory = persistenceFactory.get();
    supertype.injectMembers(object);
  }

}
