/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.sling.auth.saml2.sp;

import org.apache.sling.auth.core.spi.AuthenticationInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The <code>SessionStorage</code> class provides support to store the
 * authentication data in an HTTP Session.
 *
 * Derivative work from inner class SessionStorage from
 * https://github.com/apache/sling-org-apache-sling-auth-form/blob/master/src/main/java/org/apache/sling/auth/form/impl/FormAuthenticationHandler.java
 */

public class SessionStorage {
    private final String sessionAttributeName;

    public SessionStorage(final String sessionAttributeName) {
        this.sessionAttributeName = sessionAttributeName;
    }

    public String extractAuthenticationInfo(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object attribute = session.getAttribute(sessionAttributeName);
            if (attribute instanceof String) {
                return (String) attribute;
            }
        }
        return null;
    }

    public void set(HttpServletRequest request, HttpServletResponse response, String authData,
                    AuthenticationInfo info) {
        // store the auth hash as a session attribute
        HttpSession session = request.getSession();
        session.setAttribute(sessionAttributeName, authData);
    }


    public void clear(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(sessionAttributeName);
        }
    }

}