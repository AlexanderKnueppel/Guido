#!/bin/sh
export PATH=$PATH:./CPAchecker-1.9-unix/lib/native
java -cp "de.tubs.isf.guido.core/target/classes/:de.tubs.isf.guido.core/libs/*:de.tubs.isf.guido.network/libs/*:de.tubs.isf.guido.network/bin:de.tubs.isf.guido.verification.systems/libs/*:de.tubs.isf.guido.workingPools/bin:de.tubs.isf.guido.logger/bin:de.tubs.isf.guido.verification.systems/bin:CPAchecker-1.9-unix/cpachecker.jar:CPAchecker-1.9-unix/lib/*:CPAchecker-1.9-unix/lib/java/runtime/*" de.tubs.isf.guido.verification.systems.cpachecker.MainClass