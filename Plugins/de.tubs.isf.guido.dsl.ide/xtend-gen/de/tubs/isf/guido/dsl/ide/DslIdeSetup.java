/**
 * generated by Xtext 2.20.0
 */
package de.tubs.isf.guido.dsl.ide;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.tubs.isf.guido.dsl.DslRuntimeModule;
import de.tubs.isf.guido.dsl.DslStandaloneSetup;
import de.tubs.isf.guido.dsl.ide.DslIdeModule;
import org.eclipse.xtext.util.Modules2;

/**
 * Initialization support for running Xtext languages as language servers.
 */
@SuppressWarnings("all")
public class DslIdeSetup extends DslStandaloneSetup {
  @Override
  public Injector createInjector() {
    DslRuntimeModule _dslRuntimeModule = new DslRuntimeModule();
    DslIdeModule _dslIdeModule = new DslIdeModule();
    return Guice.createInjector(Modules2.mixin(_dslRuntimeModule, _dslIdeModule));
  }
}
