package com.polidea.kodein.sample;

import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 1, 0}, bv={1, 0, 0}, k=2, d1={"\000\032\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\b\002\0327\020\000\032\0020\001\"\b\b\000\020\002*\0020\003*\002H\0022\031\b\004\020\004\032\023\022\004\022\002H\002\022\004\022\0020\0010\005��\006\002\b\006H��\b��\006\002\020\007��\006\b"}, d2={"afterMeasured", "", "T", "Landroid/view/View;", "f", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Landroid/view/View;Lkotlin/jvm/functions/Function1;)V", "app-compileDebugKotlin"})
public final class MainActivityKt
{
  public static final <T extends View> void afterMeasured(T $receiver, @NotNull Function1<? super T, Unit> f)
  {
    ;
    Intrinsics.checkParameterIsNotNull($receiver, "$receiver");Intrinsics.checkParameterIsNotNull(f, "f");$receiver.getViewTreeObserver().addOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)new MainActivityKt.afterMeasured.1($receiver, f));
  }
}
