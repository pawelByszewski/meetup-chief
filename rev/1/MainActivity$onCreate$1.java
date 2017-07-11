package com.polidea.kodein.sample;

import android.util.Log;
import android.view.View;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

@Metadata(mv={1, 1, 0}, bv={1, 0, 0}, k=3, d1={"\000\016\n\000\n\002\020\002\n\002\030\002\n\002\b\002\020\000\032\0020\001*\n \003*\004\030\0010\0020\002H\n��\006\002\b\004"}, d2={"<anonymous>", "", "Landroid/view/View;", "kotlin.jvm.PlatformType", "invoke"})
final class MainActivity$onCreate$1
  extends Lambda
  implements Function1<View, Unit>
{
  public static final 1 INSTANCE = new 1();
  
  public final void invoke(View $receiver)
  {
    Log.i("kabum", "Kabum");
  }
  
  MainActivity$onCreate$1()
  {
    super(1);
  }
}
