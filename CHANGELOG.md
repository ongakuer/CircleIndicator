ChangeLog
--------

##### 1.2.0
* Add properties : ```orientation```(default:horizontal) ， ```gravity```(default:center)
* Remove auto register DataSetObserver. [#54](https://github.com/ongakuer/CircleIndicator/issues/54)

 <b>Please change like this if you used dynamic adapter:</b>
    ```java
    viewpager.setAdapter(mAdapter);
    indicator.setViewPager(viewpager);
    mAdapter.registerDataSetObserver(indicator.getDataSetObserver());
    ```

##### 1.1.9  (2016-04-16)
* Fix bug ([#51](https://github.com/ongakuer/CircleIndicator/issues/51))
* Add Reset Adapter Sample
* Add LoopViewPager Sample

##### 1.1.8  (2016-01-15)
* Fix bug ([#43](https://github.com/ongakuer/CircleIndicator/pull/43))

##### 1.1.7  (2015-12-21)
* Fix bug ([#41](https://github.com/ongakuer/CircleIndicator/issues/41))

##### 1.1.6  (2015-12-03)
* Support Dynamic Adapter
* Fix bugs
* Rewrite Samples

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