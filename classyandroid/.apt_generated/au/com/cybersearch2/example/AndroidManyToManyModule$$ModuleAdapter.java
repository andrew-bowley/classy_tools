// Code generated by dagger-compiler.  Do not edit.
package au.com.cybersearch2.example;

import dagger.internal.BindingsGroup;
import dagger.internal.ModuleAdapter;
import dagger.internal.ProvidesBinding;
import javax.inject.Provider;

/**
 * A manager of modules and provides adapters allowing for proper linking and
 * instance provision of types served by {@code @Provides} methods.
 */
public final class AndroidManyToManyModule$$ModuleAdapter extends ModuleAdapter<AndroidManyToManyModule> {
  private static final String[] INJECTS = { "members/au.com.cybersearch2.classytask.WorkerRunnable", "members/au.com.cybersearch2.classyjpa.persist.PersistenceFactory", "members/au.com.cybersearch2.classydb.NativeScriptDatabaseWork", "members/au.com.cybersearch2.example.AndroidManyToMany", "members/au.com.cybersearch2.classyjpa.entity.PersistenceContainer", "members/au.com.cybersearch2.classyjpa.transaction.EntityTransactionImpl", "members/au.com.cybersearch2.classydb.ClassyOpenHelperCallbacks", };
  private static final Class<?>[] STATIC_INJECTIONS = { };
  private static final Class<?>[] INCLUDES = { };

  public AndroidManyToManyModule$$ModuleAdapter() {
    super(au.com.cybersearch2.example.AndroidManyToManyModule.class, INJECTS, STATIC_INJECTIONS, false /*overrides*/, INCLUDES, true /*complete*/, false /*library*/);
  }

  @Override
  public AndroidManyToManyModule newModule() {
    return new au.com.cybersearch2.example.AndroidManyToManyModule();
  }

  /**
   * Used internally obtain dependency information, such as for cyclical
   * graph detection.
   */
  @Override
  public void getBindings(BindingsGroup bindings, AndroidManyToManyModule module) {
    bindings.contributeProvidesBinding("au.com.cybersearch2.classyjpa.persist.PersistenceFactory", new ProvidePersistenceModuleProvidesAdapter(module));
    bindings.contributeProvidesBinding("au.com.cybersearch2.classytask.ThreadHelper", new ProvideSystemEnvironmentProvidesAdapter(module));
    bindings.contributeProvidesBinding("au.com.cybersearch2.classyapp.ResourceEnvironment", new ProvideResourceEnvironmentProvidesAdapter(module));
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
    private final AndroidManyToManyModule module;

    public ProvidePersistenceModuleProvidesAdapter(AndroidManyToManyModule module) {
      super("au.com.cybersearch2.classyjpa.persist.PersistenceFactory", IS_SINGLETON, "au.com.cybersearch2.example.AndroidManyToManyModule", "providePersistenceModule");
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
    private final AndroidManyToManyModule module;

    public ProvideSystemEnvironmentProvidesAdapter(AndroidManyToManyModule module) {
      super("au.com.cybersearch2.classytask.ThreadHelper", IS_SINGLETON, "au.com.cybersearch2.example.AndroidManyToManyModule", "provideSystemEnvironment");
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

  /**
   * A {@code Binding<au.com.cybersearch2.classyapp.ResourceEnvironment>} implementation which satisfies
   * Dagger's infrastructure requirements including:
   *
   * Being a {@code Provider<au.com.cybersearch2.classyapp.ResourceEnvironment>} and handling creation and
   * preparation of object instances.
   */
  public static final class ProvideResourceEnvironmentProvidesAdapter extends ProvidesBinding<au.com.cybersearch2.classyapp.ResourceEnvironment>
      implements Provider<au.com.cybersearch2.classyapp.ResourceEnvironment> {
    private final AndroidManyToManyModule module;

    public ProvideResourceEnvironmentProvidesAdapter(AndroidManyToManyModule module) {
      super("au.com.cybersearch2.classyapp.ResourceEnvironment", IS_SINGLETON, "au.com.cybersearch2.example.AndroidManyToManyModule", "provideResourceEnvironment");
      this.module = module;
      setLibrary(false);
    }

    /**
     * Returns the fully provisioned instance satisfying the contract for
     * {@code Provider<au.com.cybersearch2.classyapp.ResourceEnvironment>}.
     */
    @Override
    public au.com.cybersearch2.classyapp.ResourceEnvironment get() {
      return module.provideResourceEnvironment();
    }
  }
}
