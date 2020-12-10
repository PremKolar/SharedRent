package com.example.sharedrent.databinding;
import com.example.sharedrent.R;
import com.example.sharedrent.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class TextRowItemBindingImpl extends TextRowItemBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.row_session_background, 9);
    }
    // views
    @NonNull
    private final androidx.constraintlayout.widget.ConstraintLayout mboundView0;
    @NonNull
    private final android.widget.TextView mboundView4;
    @NonNull
    private final android.widget.TextView mboundView5;
    @NonNull
    private final android.widget.TextView mboundView6;
    @NonNull
    private final android.widget.TextView mboundView7;
    @NonNull
    private final android.widget.TextView mboundView8;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public TextRowItemBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds));
    }
    private TextRowItemBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.EditText) bindings[3]
            , (android.widget.EditText) bindings[2]
            , (android.widget.TableLayout) bindings[9]
            , (android.widget.TextView) bindings[1]
            );
        this.editableArea.setTag(null);
        this.editableIncome.setTag(null);
        this.mboundView0 = (androidx.constraintlayout.widget.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView4 = (android.widget.TextView) bindings[4];
        this.mboundView4.setTag(null);
        this.mboundView5 = (android.widget.TextView) bindings[5];
        this.mboundView5.setTag(null);
        this.mboundView6 = (android.widget.TextView) bindings[6];
        this.mboundView6.setTag(null);
        this.mboundView7 = (android.widget.TextView) bindings[7];
        this.mboundView7.setTag(null);
        this.mboundView8 = (android.widget.TextView) bindings[8];
        this.mboundView8.setTag(null);
        this.tenantsName.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.tenant == variableId) {
            setTenant((com.example.sharedrent.Tenant) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setTenant(@Nullable com.example.sharedrent.Tenant Tenant) {
        this.mTenant = Tenant;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.tenant);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
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
        java.lang.String tenantGetFundsAfterRentFormatted = null;
        java.lang.String tenantRentPercentFormatted = null;
        java.lang.String tenantGetIncomeFormatted = null;
        java.lang.String tenantGetEffectiveLivingAreaGetFormatted = null;
        java.lang.String tenantGetRentGetFormatted = null;
        com.example.sharedrent.Tenant tenant = mTenant;
        java.lang.String tenantGetRelativeLivingAreaPercentFormatted = null;
        com.example.sharedrent.Money tenantGetRent = null;
        java.lang.String tenantGetLivingAreaFormatted = null;
        java.lang.String tenantName = null;
        com.example.sharedrent.LivingArea tenantGetEffectiveLivingArea = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (tenant != null) {
                    // read tenant.getFundsAfterRentFormatted()
                    tenantGetFundsAfterRentFormatted = tenant.getFundsAfterRentFormatted();
                    // read tenant.rentPercentFormatted
                    tenantRentPercentFormatted = tenant.rentPercentFormatted;
                    // read tenant.getIncomeFormatted()
                    tenantGetIncomeFormatted = tenant.getIncomeFormatted();
                    // read tenant.getRelativeLivingAreaPercentFormatted()
                    tenantGetRelativeLivingAreaPercentFormatted = tenant.getRelativeLivingAreaPercentFormatted();
                    // read tenant.getRent()
                    tenantGetRent = tenant.getRent();
                    // read tenant.getLivingAreaFormatted()
                    tenantGetLivingAreaFormatted = tenant.getLivingAreaFormatted();
                    // read tenant.name
                    tenantName = tenant.getName();
                    // read tenant.getEffectiveLivingArea()
                    tenantGetEffectiveLivingArea = tenant.getEffectiveLivingArea();
                }


                if (tenantGetRent != null) {
                    // read tenant.getRent().getFormatted()
                    tenantGetRentGetFormatted = tenantGetRent.getFormatted();
                }
                if (tenantGetEffectiveLivingArea != null) {
                    // read tenant.getEffectiveLivingArea().getFormatted()
                    tenantGetEffectiveLivingAreaGetFormatted = tenantGetEffectiveLivingArea.getFormatted();
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.editableArea, tenantGetLivingAreaFormatted);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.editableIncome, tenantGetIncomeFormatted);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView4, tenantGetEffectiveLivingAreaGetFormatted);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView5, tenantGetRelativeLivingAreaPercentFormatted);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView6, tenantGetFundsAfterRentFormatted);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView7, tenantRentPercentFormatted);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.mboundView8, tenantGetRentGetFormatted);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.tenantsName, tenantName);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): tenant
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}