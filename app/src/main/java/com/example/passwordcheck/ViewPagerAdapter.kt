import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordcheck.R
import com.example.passwordcheck.Story
import com.example.passwordcheck.databinding.StoryItemBinding

class ViewPagerAdapter(context: Context) : RecyclerView.Adapter<PagerVH>() {

    private val stories = listOf<Story>(
        Story("new york", AppCompatResources.getDrawable(context, R.drawable.new_york), "11.01.22", "USA", "Funny place"),
        Story("new york", AppCompatResources.getDrawable(context, R.drawable.new_york), "11.01.22", "USA", "Funny place"),
        Story("new york",AppCompatResources.getDrawable(context, R.drawable.new_york), "22.02.22", "USA", "Funny place"),
        Story("new york", AppCompatResources.getDrawable(context, R.drawable.new_york), "11.02.22", "USA", "Funny place"),
        Story("new york", AppCompatResources.getDrawable(context, R.drawable.new_york), "11.10.22", "USA", "Funny place")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH =
        PagerVH(LayoutInflater.from(parent.context).inflate(R.layout.story_item, parent, false))

    override fun getItemCount(): Int = stories.size

    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.itemView.run {
        val binding = StoryItemBinding.inflate(LayoutInflater.from(context))

        binding.story.background = stories[position].image
        binding.nameStory.text = stories[position].title
        binding.categoryStory.text = stories[position].date
        //TODO location description date...
    }


}

class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView)