### SimpleMultiTypeAdapter

[![](https://jitpack.io/v/sososeen09/SimpleMultiTypeAdapter.svg)](https://jitpack.io/#sososeen09/SimpleMultiTypeAdapter)

### Getting started

Step 1 Add it in your root build.gradle at the end of repositories:
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Step 2 Add the dependency
```
dependencies {
    implementation 'com.github.sososeen09:SimpleMultiTypeAdapter:0.0.1'
}
```

### Features
- Header
- Footer
- OnItemClickListener
- OnItemChildClickListener

### How to use
1. Get the adapter
```
QuickMultiTypeAdapter quickMultiTypeAdapter = QuickMultiTypeAdapter.newInstance();
```

2. register class with ItemViewBinder

```
// one to one
quickMultiTypeAdapter.register(String.class, new BaseItemViewBinder<String, BaseMultiViewHolder>(R.layout.item_multi) {
    @Override
    public void onBindViewHolder(@NonNull BaseMultiViewHolder holder, @NonNull String item) {
        holder.setText(R.id.tv, item);
    }
});

// one to one
quickMultiTypeAdapter.register(Integer.class, new BaseItemViewBinder<Integer, BaseMultiViewHolder>(R.layout.item_multi) {
    @Override
    public void onBindViewHolder(@NonNull BaseMultiViewHolder holder, @NonNull Integer item) {
        holder.setText(R.id.tv, "this is integer item: " + item).setBackgroundColor(R.id.tv, Color.BLUE);
    }
});


// one to many
quickMultiTypeAdapter.register(UserInfo.class).to(new FemaleBinder(), new MaleBinder()).withClassLinker(new ClassLinker<UserInfo>() {
    @NonNull
    @Override
    public Class<? extends ItemViewBinder<UserInfo, ?>> index(int position, @NonNull UserInfo userInfo) {
        return userInfo.sexuality == 1 ? MaleBinder.class : FemaleBinder.class;
    }
});
```

3. set item click listener

```
quickMultiTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
    @Override
    public void onItemClick(OnClickAdapterContract adapter, View view, int position) {
        Object o = baseQuickWrapperAdapter.getItems().get(position);
        Toast.makeText(QuickMultiAdapterActivity.this, "this is " + o.getClass().getSimpleName() + " Type" + "--: " + position, Toast.LENGTH_SHORT).show();
    }
});
```

4. set new Data or addData

```
quickMultiTypeAdapter.setNewData(items);
```

5. bind RecyclerView

```
rv.setAdapter(quickMultiTypeAdapter);
```

see the [sample](https://github.com/sososeen09/SimpleMultiTypeAdapter) for more info
### Thanks

- [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
- [MultiType](https://github.com/drakeet/MultiType)
- [HeaderViewListAdapter](http://androidxref.com/7.1.2_r36/xref/frameworks/base/core/java/android/widget/HeaderViewListAdapter.java)

### 一些不错的库
- [LoadMoreWrapper](https://github.com/nukc/LoadMoreWrapper)
