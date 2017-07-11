package com.polidea.kodein.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 1, 0}, bv={1, 0, 0}, k=1, d1={"\000\030\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\002\n\000\n\002\030\002\n\000\030\0002\0020\001B\005��\006\002\020\002J\022\020\003\032\0020\0042\b\020\005\032\004\030\0010\006H\024��\006\007"}, d2={"Lcom/polidea/kodein/sample/MainActivity;", "Landroid/app/Activity;", "()V", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "app-compileDebugKotlin"})
public final class MainActivity
  extends Activity
{
  protected void onCreate(@Nullable Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    
    setContentView(2130968601);
    
    View linearLayout = findViewById(2131492944);
    View localView1 = linearLayout;
    int $i$f$afterMeasured;
    View $receiver$iv;
    $receiver$iv.getViewTreeObserver().addOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)new MainActivity.onCreate..inlined.afterMeasured.1($receiver$iv));
  }
}
