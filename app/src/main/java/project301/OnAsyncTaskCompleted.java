/*
 * Copyright (C) 2016 CMPUT301F16T18 - Alan(Xutong) Zhao, Michael(Zichun) Lin, Stephen Larsen, Yu Zhu, Zhenzhe Xu
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

package project301;

/**
 * @classname : OnAsyncTaskCompleted
 * @class Detail: Listen to the asnctask to see whether it is finished
 *
 * @Date :   18/03/2018
 * @author : Yuqi Zhang
 * @author :Julian Stys
 * @author :Yue Ma
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */


/**
 * The interface On async task completed, will be
 * called after the async event is done.
 */
public interface OnAsyncTaskCompleted {
    /**
     * On task completed.
     *
     * @param o the object will be return by the asynctask
     */
    void onTaskCompleted(Object o);
}
