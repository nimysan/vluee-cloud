/*
 * Copyright 2011-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 *
 */
package com.vluee.cloud.commons.cqrs.command.impl;

import com.vluee.cloud.commons.cqrs.annotations.Command;
import com.vluee.cloud.commons.cqrs.command.Gate;


public class StandardGate implements Gate {

    public StandardGate(RunEnvironment runEnvironment) {
        this.runEnvironment = runEnvironment;
    }

    private final RunEnvironment runEnvironment;

    private GateHistory gateHistory = new GateHistory();

    /* (non-Javadoc)
     */
    @Override
    public Object dispatch(Object command) {
        if (!gateHistory.register(command)) {
            //TODO log.info(duplicate)
            return null;//skip duplicate
        }

        if (isAsynchronous(command)) {
            //TODO add to the queue. Queue should send this command to the RunEnvironment
            return null;
        }


        return runEnvironment.run(command);
    }

    /**
     * @param command
     * @return
     */
    private boolean isAsynchronous(Object command) {
        if (!command.getClass().isAnnotationPresent(Command.class))
            return false;

        Command commandAnnotation = command.getClass().getAnnotation(Command.class);
        return commandAnnotation.asynchronous();
    }


}
