package com.polidea.kodein.sample;

import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import kotlin.Metadata;

@Metadata(mv={1, 1, 0}, bv={1, 0, 0}, k=1, d1={"\000\023\n\000\n\002\030\002\n\002\b\002\n\002\020\002\n\000*\001\000\b\n\030\0002\0020\001B\005��\006\002\020\002J\b\020\003\032\0020\004H\026��\006\005"}, d2={"com/polidea/kodein/sample/MainActivityKt$afterMeasured$1", "Landroid/view/ViewTreeObserver$OnGlobalLayoutListener;", "(Landroid/view/View;Lkotlin/jvm/functions/Function1;)V", "onGlobalLayout", "", "app-compileDebugKotlin"})
public final class MainActivity$onCreate$$inlined$afterMeasured$1
  implements ViewTreeObserver.OnGlobalLayoutListener
{
  public void onGlobalLayout()
  {
    if ((this.receiver$0.getMeasuredWidth() > 0) && (this.receiver$0.getMeasuredHeight() > 0))
    {
      this.receiver$0.getViewTreeObserver().removeOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
      View $receiver = (View)this.receiver$0;
      int $i$a$1;
      Log.i("kabum", "Kabum");
    }
  }
  
  MainActivity$onCreate$$inlined$afterMeasured$1(View arg1) {}
}