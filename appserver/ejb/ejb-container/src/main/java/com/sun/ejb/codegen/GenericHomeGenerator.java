/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2021 Contributors to the Eclipse Foundation
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package com.sun.ejb.codegen;

import com.sun.ejb.containers.GenericEJBHome;

import java.rmi.Remote;
import java.rmi.RemoteException;

import static java.lang.reflect.Modifier.ABSTRACT;
import static java.lang.reflect.Modifier.PUBLIC;
import static org.glassfish.pfl.dynamic.codegen.spi.Wrapper._String;
import static org.glassfish.pfl.dynamic.codegen.spi.Wrapper._arg;
import static org.glassfish.pfl.dynamic.codegen.spi.Wrapper._classGenerator;
import static org.glassfish.pfl.dynamic.codegen.spi.Wrapper._clear;
import static org.glassfish.pfl.dynamic.codegen.spi.Wrapper._end;
import static org.glassfish.pfl.dynamic.codegen.spi.Wrapper._interface;
import static org.glassfish.pfl.dynamic.codegen.spi.Wrapper._method;
import static org.glassfish.pfl.dynamic.codegen.spi.Wrapper._package;
import static org.glassfish.pfl.dynamic.codegen.spi.Wrapper._t;

/**
 * This class is used to generate a sub-interface of the
 * GenericEJBHome interface that will be loaded within each
 * application.
 */
public class GenericHomeGenerator extends Generator {

    /**
     * This class name is relative to the classloader used, but has always the same name.
     */
    public static final String GENERIC_HOME_CLASSNAME = GenericHomeGenerator.class.getPackageName()
        + ".GenericEJBHome_Generated";

    /**
     * Get the fully qualified name of the generated class.
     * @return the name of the generated class.
     */
    @Override
    public final String getGeneratedClassName() {
        return GENERIC_HOME_CLASSNAME;
    }


    @Override
    public Class<?> getAnchorClass() {
        return GenericHomeGenerator.class;
    }


    @Override
    public void evaluate() {
        _clear();

        String packageName = getPackageName(getGeneratedClassName());
        String simpleName = getBaseName(getGeneratedClassName());

        _package(packageName);

        _interface(PUBLIC, simpleName, _t(GenericEJBHome.class.getName()));

        _method(PUBLIC | ABSTRACT, _t(Remote.class.getName()), "create", _t(RemoteException.class.getName()));

        _arg(_String(), "generatedBusinessIntf");

        _end();

        _classGenerator() ;
    }

}
