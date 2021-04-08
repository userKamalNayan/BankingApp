package sparksfoundation.assignment.bankingapp.events;

import androidx.fragment.app.Fragment;

public class OpenFragmentEvent {
    Fragment fragment;
    boolean openFragment;
    String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public OpenFragmentEvent(Fragment fragment, boolean openFragment, String tag) {
        this.fragment = fragment;
        this.openFragment = openFragment;
        this.tag = tag;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public boolean isOpenFragment() {
        return openFragment;
    }

    public void setOpenFragment(boolean openFragment) {
        this.openFragment = openFragment;
    }
}
