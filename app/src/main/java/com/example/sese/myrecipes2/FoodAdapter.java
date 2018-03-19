package com.example.sese.myrecipes2;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder>{

    private Context Ctx;
    private List<Food> foodList;
    DBHandler db;

    public FoodAdapter(Context ctx, List<Food> foodList) {
        Ctx = ctx;
        this.foodList = foodList;
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(Ctx);
        View view = inflater.inflate(R.layout.list_layout, null);
        //Database
        db = new DBHandler(Ctx);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, final int position) {
        final Food food = foodList.get(position);
        holder.textViewTitle.setText(food.getTitle());
        holder.textViewDescription.setText(food.getDescription());
        //click on image button
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PopupMenu
                PopupMenu popup = new PopupMenu(Ctx, view);
                popup.getMenuInflater().inflate(R.menu.dropdown_menu, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.item1:
                                //EDIT
                                Bundle bundle = new Bundle();
                                bundle.putString("key", String.valueOf(position+1));
                                Intent intentEditRecipe = new Intent(Ctx, EditRecipe.class);
                                intentEditRecipe.putExtras(bundle);
                                Ctx.getApplicationContext().startActivity(intentEditRecipe);
                                break;
                            case R.id.item2:
                                //DELETE
                                createAlert(food.getKey(), position);
                                break;

                            case R.id.item3:
                                //SHARE
                                Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                                shareIntent.setType("text/plain");
                                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, food.getTitle());
                                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, food.getTitle()+"\n\n"+
                                        food.getDescription());
                                Ctx.startActivity(Intent.createChooser(shareIntent, "Share via"));

                                break;
                        }
                        return true;
                    }
                });
            }
        });
        //Click on card
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentNewRecipe = new Intent(Ctx, ViewRecipe.class);
                //Bundle for passing key
                Bundle bundle = new Bundle();
                bundle.putString("key", String.valueOf(position+1));
                intentNewRecipe.putExtras(bundle);
                Ctx.getApplicationContext().startActivity(intentNewRecipe);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    //View holder for the food
    class FoodViewHolder extends RecyclerView.ViewHolder{
        TextView textViewTitle, textViewDescription;
        ImageView icon;
        RelativeLayout relativeLayout;
        public FoodViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            icon = itemView.findViewById(R.id.menuImageButton);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }

    //Alert for deleting
    public void createAlert(final int i, final int j){
        AlertDialog.Builder builder = new AlertDialog.Builder(Ctx);
        builder.setCancelable(true);
        builder.setTitle("Deleting recipe");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //DELETE
                        db.deleteFood(i);
                        foodList.remove(j);
                        notifyItemRemoved(j);
                        notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(Ctx, "Delete Successful", Toast.LENGTH_SHORT).show();
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //DO NOTHING
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
