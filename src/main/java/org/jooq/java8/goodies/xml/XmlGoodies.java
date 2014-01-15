/**
 * Copyright (c) 2011-2014, Data Geekery GmbH, contact@datageekery.com
 * All rights reserved.
 *
 * This software is licensed to you under the Apache License, Version 2.0
 * (the "License"); You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * . Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * . Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * . Neither the name "jOOU" nor the names of its contributors may be
 *   used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.jooq.java8.goodies.xml;

import static org.joox.JOOX.$;

import java.io.File;

/**
 * @author Lukas Eder
 */
public class XmlGoodies {
    public static void main(String[] args) throws Exception {
        System.out.println("Find all artifacts and print them in 'Maven notation'");
        System.out.println("-----------------------------------------------------");
        $(new File("./pom.xml"))
            .find("groupId")
            .each(ctx -> {
                System.out.println(
                $(ctx).text() + ":" +
                $(ctx).siblings("artifactId").text() + ":" +
                $(ctx).siblings("version").text());
            });

        System.out.println();
        System.out.println("Find all non-SNAPSHOT artifacts and print them in 'Maven notation'");
        System.out.println("------------------------------------------------------------------");
        $(new File("./pom.xml"))
            .find("groupId")
            .filter(ctx -> $(ctx).siblings("version")
                    .matchText(".*-SNAPSHOT")
                    .isEmpty())
            .each(ctx -> {
                System.out.println(
                $(ctx).text() + ":" +
                $(ctx).siblings("artifactId").text() + ":" +
                $(ctx).siblings("version").text());
            });

        System.out.println();
        System.out.println("Find all non-SNAPSHOT artifacts that aren't dependencies and print them in 'Maven notation'");
        System.out.println("-------------------------------------------------------------------------------------------");
        $(new File("./pom.xml"))
            .find("groupId")
            .filter(ctx -> $(ctx).siblings("version")
                                 .matchText(".*-SNAPSHOT")
                                 .isEmpty())
            .content(ctx ->
                $(ctx).text() + ":" +
                $(ctx).siblings("artifactId").text() + ":" +
                $(ctx).siblings("version").text()
            )
            .rename("artifact")
            .each(ctx -> System.out.println(ctx));
    }
}
