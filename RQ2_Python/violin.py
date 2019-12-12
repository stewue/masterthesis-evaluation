import matplotlib.pyplot as plt
import seaborn as sns
import pandas as pd

data = {'x': [
        'different', 'different', 'different', 'different', 'different', 'different', 'different', 'different', 'different', 'different',
        'different', 'different', 'different', 'different', 'different', 'different', 'different', 'different', 'different', 'different',
        'same', 'same', 'same', 'same', 'same', 'same', 'same', 'same', 'same', 'same',
        'same', 'same', 'same', 'same', 'same', 'same', 'same', 'same', 'same', 'same'
],
        'Type': [
            "before", "before", "before", "before", "before", "before", "before", "before", "before", "before",
            "after", "after", "after", "after", "after", "after", "after", "after", "after", "after",
            "before", "before", "before", "before", "before", "before", "before", "before", "before", "before",
            "after", "after", "after", "after", "after", "after", "after", "after", "after", "after",
        ],
        'y' : [
            100, 90, 80, 95, 110, 105, 103, 97, 100, 110,
            70, 85, 75, 90, 105, 100, 97, 92, 95, 85,
            100, 90, 80, 95, 110, 105, 103, 97, 100, 110,
            95, 90, 80, 95, 110, 105, 103, 97, 105, 110 ]}

df = pd.DataFrame(data)

ax = sns.violinplot(x="x", y="y", hue="Type", data=df, split=True)
ax.axes.get_yaxis().set_visible(False)
ax.set_xlabel('')
plt.tight_layout()
# plt.show()
plt.savefig('export.png')
