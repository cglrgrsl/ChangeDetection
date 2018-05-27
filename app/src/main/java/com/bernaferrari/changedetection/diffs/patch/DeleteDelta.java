/*-
 * #%L
 * java-diff-utils
 * %%
 * Copyright (C) 2009 - 2017 java-diff-utils
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 * #L%
 */
package com.bernaferrari.changedetection.diffs.patch;

import java.util.List;

/**
 * Describes the delete-delta between original and revised texts.
 *
 * @param T The type of the compared elements in the 'lines'.
 * @author <a href="dm.naumenko@gmail.com">Dmitry Naumenko</a>
 */
public final class DeleteDelta<T> extends Delta<T> {

    /**
     * Creates a change delta with the two given chunks.
     *
     * @param original The original chunk. Must not be {@code null}.
     * @param revised  The original chunk. Must not be {@code null}.
     */
    public DeleteDelta(Chunk<T> original, Chunk<T> revised) {
        super(DeltaType.DELETE, original, revised);
    }

    @Override
    public void applyTo(List<T> target) throws PatchFailedException {
        verify(target);
        int position = getOriginal().getPosition();
        int size = getOriginal().size();
        for (int i = 0; i < size; i++) {
            target.remove(position);
        }
    }

    @Override
    public void restore(List<T> target) {
        int position = this.getRevised().getPosition();
        List<T> lines = this.getOriginal().getLines();
        for (int i = 0; i < lines.size(); i++) {
            target.add(position + i, lines.get(i));
        }
    }

    @Override
    public String toString() {
        return "[DeleteDelta, position: " + getOriginal().getPosition() + ", lines: "
                + getOriginal().getLines() + "]";
    }
}
