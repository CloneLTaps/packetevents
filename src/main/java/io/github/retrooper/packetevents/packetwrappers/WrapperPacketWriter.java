/*
 * MIT License
 *
 * Copyright (c) 2020 retrooper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.retrooper.packetevents.packetwrappers;

public interface WrapperPacketWriter {

    // TODO write for the arrays
    void writeBoolean(int index, boolean value);

    void writeByte(int index, byte value);

    void writeShort(int index, short value);

    void writeInt(int index, int value);

    void writeLong(int index, long value);

    void writeFloat(int index, float value);

    void writeDouble(int index, double value);

    void writeString(int index, String value);

    //ARRAYS
    void writeBooleanArray(int index, boolean[] array);

    void writeByteArray(int index, byte[] value);

    void writeShortArray(int index, short[] value);

    void writeIntArray(int index, int[] value);

    void writeLongArray(int index, long[] value);

    void writeFloatArray(int index, float[] value);

    void writeDoubleArray(int index, double[] value);

    void writeStringArray(int index, String[] value);

    void writeObject(int index, Object object);
}
