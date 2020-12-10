package com.example.sharedrent;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.example.sharedrent.databinding.TableHeaderBindingImpl;
import com.example.sharedrent.databinding.TextCellBindingImpl;
import com.example.sharedrent.databinding.TextRowItemBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_TABLEHEADER = 1;

  private static final int LAYOUT_TEXTCELL = 2;

  private static final int LAYOUT_TEXTROWITEM = 3;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(3);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.sharedrent.R.layout.table_header, LAYOUT_TABLEHEADER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.sharedrent.R.layout.text_cell, LAYOUT_TEXTCELL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.example.sharedrent.R.layout.text_row_item, LAYOUT_TEXTROWITEM);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_TABLEHEADER: {
          if ("layout/table_header_0".equals(tag)) {
            return new TableHeaderBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for table_header is invalid. Received: " + tag);
        }
        case  LAYOUT_TEXTCELL: {
          if ("layout/text_cell_0".equals(tag)) {
            return new TextCellBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for text_cell is invalid. Received: " + tag);
        }
        case  LAYOUT_TEXTROWITEM: {
          if ("layout/text_row_item_0".equals(tag)) {
            return new TextRowItemBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for text_row_item is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(3);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "tenant");
      sKeys.put(2, "text");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(3);

    static {
      sKeys.put("layout/table_header_0", com.example.sharedrent.R.layout.table_header);
      sKeys.put("layout/text_cell_0", com.example.sharedrent.R.layout.text_cell);
      sKeys.put("layout/text_row_item_0", com.example.sharedrent.R.layout.text_row_item);
    }
  }
}
