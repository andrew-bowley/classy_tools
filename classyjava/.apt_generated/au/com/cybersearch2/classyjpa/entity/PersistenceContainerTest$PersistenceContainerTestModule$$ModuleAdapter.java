// Code generated by dagger-compiler.  Do not edit.
package au.com.cybersearch2.classyjpa.entity;

import dagger.internal.BindingsGroup;
import dagger.internal.ModuleAdapter;
import dagger.internal.ProvidesBinding;
import javax.inject.Provider;

/**
 * A manager of modules and provides adapters allowing for proper linking and
 * instance provision of types served by {@code @Provides} methods.
 */
public final class PersistenceContainerTest$PersistenceContainerTestModule$$ModuleAdapter extends ModuleAdapter<PersistenceContainerTest.PersistenceContainerTestModule> {
  private static final String[] INJECTS = { "members/au.com.cybersearch2.classyjpa.entity.PersistenceContainer", "members/au.com.cybersearch2.classytask.WorkerRunnable", };
  private static final Class<?>[] STATIC_INJECTIONS = { };
  private static final Class<?>[] INCLUDES = { };

  public PersistenceContainerTest$PersistenceContainerTestModule$$ModuleAdapter() {
    super(au.com.cybersearch2.classyjpa.entity.PersistenceContainerTest.PersistenceContainerTestModule.class, INJECTS, STATIC_INJECTIONS, false /*overrides*/, INCLUDES, true /*complete*/, false /*library*/);
  }

  @Override
  public PersistenceContainerTest.PersistenceContainerTestModule newModule() {
    return new au.com.cybersearch2.classyjpa.entity.PersistenceContainerTest.PersistenceContainerTestModule();
  }

  /**
   * Used internally obtain dependency information, such as for cyclical
   * graph detection.
   */
  @Override
  public void getBindings(BindingsGroup bindings, PersistenceContainerTest.PersistenceContainerTestModule module) {
    bindings.contributeProvidesBinding("au.com.cybersearch2.classyjpa.persist.PersistenceFactory", new ProvidePersistenceModuleProvidesAdapter(module));
    bindings.contributeProvidesBinding("au.com.cybersearch2.classytask.ThreadHelper", new ProvideSystemEnvironmentProvidesAdapter(module));
  }

  /**
   * A {@code Binding<au.com.cybersearch2.classyjpa.persist.PersistenceFactory>} implementation which satisfies
   * Dagger's infrastructure requirements including:
   *
   * Being a {@code Provider<au.com.cybersearch2.classyjpa.persist.PersistenceFactory>} and handling creation and
   * preparation of object instances.
   */
  public static final class ProvidePersistenceModuleProvidesAdapter extends ProvidesBinding<au.com.cybersearch2.classyjpa.persist.PersistenceFactory>
      implements Provider<au.com.cybersearch2.classyjpa.persist.PersistenceFactory> {
    private final PersistenceContainerTest.PersistenceContainerTestModule module;

    public ProvidePersistenceModuleProvidesAdapter(PersistenceContainerTest.PersistenceContainerTestModule module) {
      super("au.com.cybersearch2.classyjpa.persist.PersistenceFactory", IS_SINGLETON, "au.com.cybersearch2.classyjpa.entity.PersistenceContainerTest.PersistenceContainerTestModule", "providePersistenceModule");
      this.module = module;
      setLibrary(false);
    }

    /**
     * Returns the fully provisioned instance satisfying the contract for
     * {@code Provider<au.com.cybersearch2.classyjpa.persist.PersistenceFactory>}.
     */
    @Override
    public au.com.cybersearch2.classyjpa.persist.PersistenceFactory get() {
      return module.providePersistenceModule();
    }
  }

  /**
   * A {@code Binding<au.com.cybersearch2.classytask.ThreadHelper>} implementation which satisfies
   * Dagger's infrastructure requirements including:
   *
   * Being a {@code Provider<au.com.cybersearch2.classytask.ThreadHelper>} and handling creation and
   * preparation of object instances.
   */
  public static final class ProvideSystemEnvironmentProvidesAdapter extends ProvidesBinding<au.com.cybersearch2.classytask.ThreadHelper>
      implements Provider<au.com.cybersearch2.classytask.ThreadHelper> {
    private final PersistenceContainerTest.PersistenceContainerTestModule module;

    public ProvideSystemEnvironmentProvidesAdapter(PersistenceContainerTest.PersistenceContainerTestModule module) {
      super("au.com.cybersearch2.classytask.ThreadHelper", IS_SINGLETON, "au.com.cybersearch2.classyjpa.entity.PersistenceContainerTest.PersistenceContainerTestModule", "provideSystemEnvironment");
      this.module = module;
      setLibrary(false);
    }

    /**
     * Returns the fully provisioned instance satisfying the contract for
     * {@code Provider<au.com.cybersearch2.classytask.ThreadHelper>}.
     */
    @Override
    public au.com.cybersearch2.classytask.ThreadHelper get() {
      return module.provideSystemEnvironment();
    }
  }
}
