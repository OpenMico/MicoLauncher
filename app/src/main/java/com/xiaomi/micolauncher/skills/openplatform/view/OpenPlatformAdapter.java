package com.xiaomi.micolauncher.skills.openplatform.view;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.skills.openplatform.model.OpenPlatformModel;
import com.xiaomi.micolauncher.skills.openplatform.model.SkillChatItem;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class OpenPlatformAdapter extends RecyclerView.Adapter<a> {
    private List<SkillChatItem> a;
    private int b;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class a extends RecyclerView.ViewHolder {
        LinearLayout a;
        ImageView b;
        TextView c;

        a(View view) {
            super(view);
            this.a = (LinearLayout) view.findViewById(R.id.linearLayoutChatItem);
            this.b = (ImageView) view.findViewById(R.id.imageViewIcon);
            this.c = (TextView) view.findViewById(R.id.textViewSkillChatContent);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public OpenPlatformAdapter(List<SkillChatItem> list) {
        this.a = list;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(List<SkillChatItem> list) {
        this.a = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NotNull
    public a onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.openplatform_chat_list_item, viewGroup, false);
        if (this.b == 0) {
            this.b = inflate.getContext().getResources().getDimensionPixelSize(R.dimen.openplatform_chat_item_text_padding);
        }
        return new a(inflate);
    }

    public void onBindViewHolder(@NonNull a aVar, int i) {
        List<SkillChatItem> list = this.a;
        if (list == null || i >= list.size()) {
            L.base.w("invalid position in OpenPlatformAdapter.");
            return;
        }
        SkillChatItem skillChatItem = this.a.get(i);
        if (skillChatItem.getType() == SkillChatItem.Type.RECV) {
            aVar.a.setGravity(GravityCompat.START);
            aVar.b.setVisibility(0);
            Drawable skillIcon = OpenPlatformModel.getInstance().getSkillIcon();
            if (skillIcon != null) {
                aVar.b.setImageDrawable(skillIcon);
            } else {
                L.base.w("skill icon null");
                GlideUtils.bindImageView(aVar.b.getContext(), skillChatItem.avatar, aVar.b);
            }
            aVar.c.setTextColor(aVar.itemView.getResources().getColor(R.color.chat_content_receive_color, null));
            aVar.c.setBackgroundResource(R.drawable.chat_recv_bg);
        } else {
            aVar.a.setGravity(GravityCompat.END);
            aVar.b.setVisibility(8);
            aVar.c.setTextColor(aVar.itemView.getResources().getColor(R.color.chat_content_send_color, null));
            aVar.c.setBackgroundResource(R.drawable.skill_lquery);
        }
        TextView textView = aVar.c;
        int i2 = this.b;
        textView.setPadding(i2, i2, i2, i2);
        aVar.c.setText(skillChatItem.getContent());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return ContainerUtil.getSize(this.a);
    }
}
