package com.scrapshubvendor.api.interfaces;

import com.scrapshubvendor.activities.MainActivity;
import com.scrapshubvendor.api.ApplicationModule;
import com.scrapshubvendor.api.HttpModule;
import com.scrapshubvendor.fragments.AboutUsFragment;
import com.scrapshubvendor.fragments.CreditHistoryFragment;
import com.scrapshubvendor.fragments.EditProfileFragment;
import com.scrapshubvendor.fragments.ForgotPasswordFragment;
import com.scrapshubvendor.fragments.LoginFragment;
import com.scrapshubvendor.fragments.OrderDetailFragment;
import com.scrapshubvendor.fragments.OrderFragment;
import com.scrapshubvendor.fragments.PackagesFragment;
import com.scrapshubvendor.fragments.RegisterFragment;
import javax.inject.Singleton;
import dagger.Component;

@Singleton
@Component(modules = {HttpModule.class, ApplicationModule.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);
  void inject(OrderFragment orderFragment);
  void inject(EditProfileFragment editProfileFragment);

  void inject(CreditHistoryFragment creditHistoryFragment);
  void inject(OrderDetailFragment orderDetailFragment);
  void inject(PackagesFragment packagesFragment);
  void inject(LoginFragment loginFragment);

 void inject(RegisterFragment registerFragment);
 void inject(AboutUsFragment aboutUsFragment);
    void inject(ForgotPasswordFragment forgotPasswordFragment);

}
