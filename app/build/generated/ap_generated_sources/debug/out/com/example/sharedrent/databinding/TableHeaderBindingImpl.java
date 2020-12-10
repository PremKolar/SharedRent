package com.example.sharedrent.databinding;
import com.example.sharedrent.R;
import com.example.sharedrent.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class TableHeaderBindingImpl extends TableHeaderBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new androidx.databinding.ViewDataBinding.IncludedLayouts(9);
        sIncludes.setIncludes(1, 
            new String[] {"text_cell", "text_cell", "text_cell", "text_cell", "text_cell", "text_cell", "text_cell"},
            new int[] {2, 3, 4, 5, 6, 7, 8},
            new int[] {com.example.sharedrent.R.layout.text_cell,
                com.example.sharedrent.R.layout.text_cell,
                com.example.sharedrent.R.layout.text_cell,
                com.example.sharedrent.R.layout.text_cell,
                com.example.sharedrent.R.layout.text_cell,
                com.example.sharedrent.R.layout.text_cell,
                com.example.sharedrent.R.layout.text_cell});
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final android.widget.FrameLayout mboundView0;
    @NonNull
    private final android.widget.TableRow mboundView1;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public TableHeaderBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds));
    }
    private TableHeaderBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 7
            , (com.example.sharedrent.databinding.TextCellBinding) bindings[8]
            , (com.example.sharedrent.databinding.TextCellBinding) bindings[4]
            , (com.example.sharedrent.databinding.TextCellBinding) bindings[5]
            , (com.example.sharedrent.databinding.TextCellBinding) bindings[6]
            , (com.example.sharedrent.databinding.TextCellBinding) bindings[7]
            , (com.example.sharedrent.databinding.TextCellBinding) bindings[3]
            , (com.example.sharedrent.databinding.TextCellBinding) bindings[2]
            );
        setContainedBinding(this.headerCalculatedRent);
        setContainedBinding(this.headerIncome);
        setContainedBinding(this.headerLivingArea);
        setContainedBinding(this.headerPercentLivingArea);
        setContainedBinding(this.headerPercentRent);
        setContainedBinding(this.headerTenentName);
        setContainedBinding(this.headerTenentNamsdfgfde);
        this.mboundView0 = (android.widget.FrameLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (android.widget.TableRow) bindings[1];
        this.mboundView1.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x80L;
        }
        headerTenentNamsdfgfde.invalidateAll();
        headerTenentName.invalidateAll();
        headerIncome.invalidateAll();
        headerLivingArea.invalidateAll();
        headerPercentLivingArea.invalidateAll();
        headerPercentRent.invalidateAll();
        headerCalculatedRent.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (headerTenentNamsdfgfde.hasPendingBindings()) {
            return true;
        }
        if (headerTenentName.hasPendingBindings()) {
            return true;
        }
        if (headerIncome.hasPendingBindings()) {
            return true;
        }
        if (headerLivingArea.hasPendingBindings()) {
            return true;
        }
        if (headerPercentLivingArea.hasPendingBindings()) {
            return true;
        }
        if (headerPercentRent.hasPendingBindings()) {
            return true;
        }
        if (headerCalculatedRent.hasPendingBindings()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    public void setLifecycleOwner(@Nullable androidx.lifecycle.LifecycleOwner lifecycleOwner) {
        super.setLifecycleOwner(lifecycleOwner);
        headerTenentNamsdfgfde.setLifecycleOwner(lifecycleOwner);
        headerTenentName.setLifecycleOwner(lifecycleOwner);
        headerIncome.setLifecycleOwner(lifecycleOwner);
        headerLivingArea.setLifecycleOwner(lifecycleOwner);
        headerPercentLivingArea.setLifecycleOwner(lifecycleOwner);
        headerPercentRent.setLifecycleOwner(lifecycleOwner);
        headerCalculatedRent.setLifecycleOwner(lifecycleOwner);
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeHeaderTenentNamsdfgfde((com.example.sharedrent.databinding.TextCellBinding) object, fieldId);
            case 1 :
                return onChangeHeaderTenentName((com.example.sharedrent.databinding.TextCellBinding) object, fieldId);
            case 2 :
                return onChangeHeaderIncome((com.example.sharedrent.databinding.TextCellBinding) object, fieldId);
            case 3 :
                return onChangeHeaderPercentLivingArea((com.example.sharedrent.databinding.TextCellBinding) object, fieldId);
            case 4 :
                return onChangeHeaderPercentRent((com.example.sharedrent.databinding.TextCellBinding) object, fieldId);
            case 5 :
                return onChangeHeaderCalculatedRent((com.example.sharedrent.databinding.TextCellBinding) object, fieldId);
            case 6 :
                return onChangeHeaderLivingArea((com.example.sharedrent.databinding.TextCellBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeHeaderTenentNamsdfgfde(com.example.sharedrent.databinding.TextCellBinding HeaderTenentNamsdfgfde, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x1L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeHeaderTenentName(com.example.sharedrent.databinding.TextCellBinding HeaderTenentName, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x2L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeHeaderIncome(com.example.sharedrent.databinding.TextCellBinding HeaderIncome, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x4L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeHeaderPercentLivingArea(com.example.sharedrent.databinding.TextCellBinding HeaderPercentLivingArea, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x8L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeHeaderPercentRent(com.example.sharedrent.databinding.TextCellBinding HeaderPercentRent, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x10L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeHeaderCalculatedRent(com.example.sharedrent.databinding.TextCellBinding HeaderCalculatedRent, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x20L;
            }
            return true;
        }
        return false;
    }
    private boolean onChangeHeaderLivingArea(com.example.sharedrent.databinding.TextCellBinding HeaderLivingArea, int fieldId) {
        if (fieldId == BR._all) {
            synchronized(this) {
                    mDirtyFlags |= 0x40L;
            }
            return true;
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
        if ((dirtyFlags & 0x80L) != 0) {
            // api target 1

            this.headerCalculatedRent.setText(getRoot().getResources().getString(R.string.header_calculatedRent));
            this.headerIncome.setText(getRoot().getResources().getString(R.string.header_income));
            this.headerLivingArea.setText(getRoot().getResources().getString(R.string.header_livingArea));
            this.headerPercentLivingArea.setText(getRoot().getResources().getString(R.string.header_percent_livingArea));
            this.headerPercentRent.setText(getRoot().getResources().getString(R.string.header_percent_rent));
            this.headerTenentName.setText(getRoot().getResources().getString(R.string.header_tenant_name));
            this.headerTenentNamsdfgfde.setText("sdvsddddddvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
        }
        executeBindingsOn(headerTenentNamsdfgfde);
        executeBindingsOn(headerTenentName);
        executeBindingsOn(headerIncome);
        executeBindingsOn(headerLivingArea);
        executeBindingsOn(headerPercentLivingArea);
        executeBindingsOn(headerPercentRent);
        executeBindingsOn(headerCalculatedRent);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): headerTenentNamsdfgfde
        flag 1 (0x2L): headerTenentName
        flag 2 (0x3L): headerIncome
        flag 3 (0x4L): headerPercentLivingArea
        flag 4 (0x5L): headerPercentRent
        flag 5 (0x6L): headerCalculatedRent
        flag 6 (0x7L): headerLivingArea
        flag 7 (0x8L): null
    flag mapping end*/
    //end
}