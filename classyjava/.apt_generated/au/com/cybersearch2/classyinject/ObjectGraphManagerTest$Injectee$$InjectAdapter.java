// Code generated by dagger-compiler.  Do not edit.
package au.com.cybersearch2.classyinject;

import dagger.MembersInjector;
import dagger.internal.Binding;
import dagger.internal.Linker;
import java.util.Set;
import javax.inject.Provider;

/**
 * A {@code Binding<ObjectGraphManagerTest.Injectee>} implementation which satisfies
 * Dagger's infrastructure requirements including:
 *
 * Owning the dependency links between {@code ObjectGraphManagerTest.Injectee} and its
 * dependencies.
 *
 * Being a {@code Provider<ObjectGraphManagerTest.Injectee>} and handling creation and
 * preparation of object instances.
 *
 * Being a {@code MembersInjector<ObjectGraphManagerTest.Injectee>} and handling injection
 * of annotated fields.
 */
public final class ObjectGraphManagerTest$Injectee$$InjectAdapter extends Binding<ObjectGraphManagerTest.Injectee>
    implements Provider<ObjectGraphManagerTest.Injectee>, MembersInjector<ObjectGraphManagerTest.Injectee> {
  private Binding<Object> object;

  public ObjectGraphManagerTest$Injectee$$InjectAdapter() {
    super("au.com.cybersearch2.classyinject.ObjectGraphManagerTest$Injectee", "members/au.com.cybersearch2.classyinject.ObjectGraphManagerTest$Injectee", NOT_SINGLETON, ObjectGraphManagerTest.Injectee.class);
  }

  /**
   * Used internally to link bindings/providers together at run time
   * according to their dependency graph.
   */
  @Override
  @SuppressWarnings("unchecked")
  public void attach(Linker linker) {
    object = (Binding<Object>) linker.requestBinding("java.lang.Object", ObjectGraphManagerTest.Injectee.class, getClass().getClassLoader());
  }

  /**
   * Used internally obtain dependency information, such as for cyclical
   * graph detection.
   */
  @Override
  public void getDependencies(Set<Binding<?>> getBindings, Set<Binding<?>> injectMembersBindings) {
    injectMembersBindings.add(object);
  }

  /**
   * Returns the fully provisioned instance satisfying the contract for
   * {@code Provider<ObjectGraphManagerTest.Injectee>}.
   */
  @Override
  public ObjectGraphManagerTest.Injectee get() {
    ObjectGraphManagerTest.Injectee result = new ObjectGraphManagerTest.Injectee();
    injectMembers(result);
    return result;
  }

  /**
   * Injects any {@code @Inject} annotated fields in the given instance,
   * satisfying the contract for {@code Provider<ObjectGraphManagerTest.Injectee>}.
   */
  @Override
  public void injectMembers(ObjectGraphManagerTest.Injectee object) {
    object.object = object.get();
  }

}
