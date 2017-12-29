/*
 * MIT License
 *
 * Copyright (c) 2017 Stamina Development
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

package pw.stamina.minecraftapi;

import pw.stamina.minecraftapi.service.MinecraftApiService;
import pw.stamina.minecraftapi.service.MinecraftApiServiceRegistry;

//Services:
// - PacketCreationService
// - BoundingBoxCreationService
// - MinecraftLocatorService

//TODO: Check if standard services are available
public final class MinecraftApi {

    public static void bootstrap() {
        //TODO: Bootstrap project(s) dependent using this API
    }

    public static <T> void emitEvent(T event) {
        //TODO: Delegate events
    }

    public static <T extends MinecraftApiService> T getService(Class<T> serviceClass) {
        return MinecraftApiServiceRegistry.getService(serviceClass);
    }
}
