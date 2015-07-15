CircleIndicator
===============
a lightweight viewpager indicator like in nexus 5 launcher 

![CircleIndicator](/screenshot.gif)

Gradle
------------

**version 1.1.5 (```minSdkVersion="11"```)**
```groovy
dependencies {
    compile 'me.relex:circleindicator:1.1.5@aar'
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



ChangeLog
--------

##### 1.1.5  (2015-06-28)
* Fix bug ([#22](https://github.com/ongakuer/CircleIndicator/issues/22))

##### 1.1.4  (2015-06-03)
* Fix bug ([#17](https://github.com/ongakuer/CircleIndicator/issues/17))
* SetOnPageChangeListener is deprecated, Please use ViewPager addOnPageChangeListener (v4 support lirbray revision 22.2.0)
* ```minSdkVersion="11"```

##### 1.1.3  (2015-04-15)
* Fix bug ([#12](https://github.com/ongakuer/CircleIndicator/issues/12))

##### 1.1.2  (2015-03-10)
* Fix bug ([#9](https://github.com/ongakuer/CircleIndicator/pull/9))
* Add a property : ```ci_drawable_unselected``` ([#6](https://github.com/ongakuer/CircleIndicator/pull/6))

##### 1.1.1  (2015-01-08)
* Fix default animator
* Add a property : ```ci_animator_reverse```

##### 1.1.0  (2014-12-29)
* Remove NineOldAndroids dependency
* ```minSdkVersion="14"```

##### 1.0.0  (2014-08-01)
* Initial release. (```minSdkVersion="8"```)


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
