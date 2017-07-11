package com.polidea.kodein.sample;

import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;

@Metadata(mv={1, 1, 0}, bv={1, 0, 0}, k=1, d1={"\000\023\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\000*\001\000\b\n\030\0002\0020\001B\005��\006\002\020\002J\b\020\003\032\0020\004H\026��\006\005"}, d2={"com/polidea/kodein/sample/MainActivityKt$afterMeasured$1", "Landroid/view/ViewTreeObserver$OnGlobalLayoutListener;", "(Landroid/view/View;Lkotlin/jvm/functions/Function1;)V", "onGlobalLayout", "", "app-compileDebugKotlin"})
public final class MainActivityKt$afterMeasured$1
  implements ViewTreeObserver.OnGlobalLayoutListener
{
  public void onGlobalLayout()
  {
    if ((this.receiver$0.getMeasuredWidth() > 0) && (this.receiver$0.getMeasuredHeight() > 0))
    {
      this.receiver$0.getViewTreeObserver().removeOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
      this.$f.invoke(this.receiver$0);
    }
  }
  
  MainActivityKt$afterMeasured$1(T $receiver, Function1 $captured_local_variable$1) {}
}
