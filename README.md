CircleIndicator
===============
a lightweight viewpager indicator like in nexus 5 launcher 

![CircleIndicator](/screenshot.gif)

Gradle
------------

**version 1.1.1 (```minSdkVersion="14"```)**
```groovy
dependencies {
    compile 'me.relex:circleindicator:1.1.1@aar'
}
```


**version 1.0.0 (```minSdkVersion="8"```)**
```groovy
dependencies {
    compile 'com.nineoldandroids:library:2.4.+'
    compile 'me.relex:circleindicator:1.0.0@aar'
}
```

Usage
--------
```xml
	<me.relex.circleindicator.CircleIndicator
        android:layout_width="fill_parent"
        android:layout_height="40dp"/>
```

#####Properties:

* `app:ci_width`
* `app:ci_height`
* `app:ci_margin`
* `app:ci_drawable`
* `app:ci_drawable_unselected`
* `app:ci_animator`
* `app:ci_animator_reverse`


License
--------
```
Copyright (C) 2014 relex

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
