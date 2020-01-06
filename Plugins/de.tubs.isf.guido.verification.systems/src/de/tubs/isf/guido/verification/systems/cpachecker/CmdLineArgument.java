/*
 *  CPAchecker is a tool for configurable software verification.
 *  This file is part of CPAchecker.
 *
 *  Copyright (C) 2007-2016  Dirk Beyer
 *  All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 *  CPAchecker web page:
 *    http://cpachecker.sosy-lab.org
 */
package de.tubs.isf.guido.verification.systems.cpachecker;

import static de.tubs.isf.guido.verification.systems.cpachecker.CmdLineArguments.putIfNotExistent;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import de.tubs.isf.guido.verification.systems.cpachecker.CmdLineArguments.InvalidCmdlineArgumentException;

abstract class CmdLineArgument implements Comparable<CmdLineArgument> {

  private final ImmutableSet<String> names;
  private String description = ""; // changed later, if needed

  CmdLineArgument(String... pNames) {
    names = ImmutableSet.copyOf(pNames);
  }

  CmdLineArgument withDescription(String pDescription) {
    description = pDescription;
    return this;
  }

  @Override
  public int compareTo(CmdLineArgument other) {
    return names.toString().compareTo(other.names.toString());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    return o instanceof CmdLineArgument && names.equals(((CmdLineArgument) o).names);
  }

  @Override
  public int hashCode() {
    return names.hashCode();
  }

  @Override
  public String toString() {
    String s = Joiner.on("/").join(names);
    if (description.isEmpty()) {
      return s;
    } else {
      // we rjust the description at column 20.
      return String.format("%1$-20s %2$s", s, description);
    }
  }

  boolean apply(
      Map<String, String> properties,
      String currentArg,
      Iterator<String> argsIt)
      throws InvalidCmdlineArgumentException {
    if (names.contains(currentArg)) {
      apply0(properties, currentArg, argsIt);
      return true;
    }
    return false;
  }

  abstract void apply0(
      Map<String, String> properties,
      String currentArg,
      Iterator<String> argsIt)
      throws InvalidCmdlineArgumentException;

  /** The arg is a short replacement for an option with 'one' value given as next argument. */
  static class CmdLineArgument1 extends CmdLineArgument {

    private final String option;

    CmdLineArgument1(String pName) {
      super(pName);
      option = "";
    }

    CmdLineArgument1(String pName, String pOption) {
      super(pName);
      option = pOption;
    }

    CmdLineArgument1(String pName1, String pName2, String pOption) {
      super(pName1, pName2);
      option = pOption;
    }

    String getOption() {
      return option;
    }

    @Override
    final void apply0(
        Map<String, String> properties,
        String currentArg,
        Iterator<String> args)
        throws InvalidCmdlineArgumentException {
      if (args.hasNext()) {
        handleArg(properties, args.next());
      } else {
        throw new InvalidCmdlineArgumentException(currentArg + " argument missing.");
      }
    }

    /**
     * Handles a command-line argument.
     *
     * @param pProperties the map of configuration properties.
     * @param pArg the value of the configuration option represented by this argument.
     */
    void handleArg(
        Map<String, String> pProperties,
        String pArg)
        throws InvalidCmdlineArgumentException {
      putIfNotExistent(pProperties, option, pArg);
    }
  }

  /** This is a command-line argument that sets some properties to fixed values. */
  static class PropertyAddingCmdLineArgument extends CmdLineArgument {

    private final Map<String, String> additionalIfNotExistentArgs = new HashMap<>();
    private final Map<String, String> additionalArgs = new HashMap<>();

    PropertyAddingCmdLineArgument(String pName) {
      super(pName);
    }

    PropertyAddingCmdLineArgument settingProperty(String pName, String pValue) {
      additionalIfNotExistentArgs.put(pName, pValue);
      return this;
    }

    PropertyAddingCmdLineArgument overridingProperty(String pName, String pValue) {
      additionalArgs.put(pName, pValue);
      return this;
    }

    @Override
    void apply0(
        Map<String, String> properties,
        String currentArg,
        Iterator<String> args)
        throws InvalidCmdlineArgumentException {
      for (Entry<String, String> e : additionalIfNotExistentArgs.entrySet()) {
        putIfNotExistent(properties, e.getKey(), e.getValue());
      }
      for (Entry<String, String> e : additionalArgs.entrySet()) {
        properties.put(e.getKey(), e.getValue());
      }
    }
  }
}
