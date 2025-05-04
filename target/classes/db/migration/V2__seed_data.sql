-- Insert admin user (password: admin123)
INSERT INTO users (name, email, password, role, created_at)
VALUES ('Admin User', 'admin@byteandblog.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'ADMIN', NOW());

-- Insert regular user (password: user123)
INSERT INTO users (name, email, password, role, created_at)
VALUES ('Regular User', 'user@byteandblog.com', '$2a$10$yRxRYK/S1MUAYSqb7sF2dezBM/f5J.zdYEcv.9XnZ7Q/Qqr8.hGT.', 'USER', NOW());

-- Insert sample blog posts
INSERT INTO blog_posts (title, excerpt, content, cover_image, author_id, published_at, created_at)
VALUES (
    'Getting Started with React Hooks',
    'Learn how to use React Hooks to simplify your components and manage state effectively.',
    '<p>React Hooks are a powerful feature that allows you to use state and other React features without writing a class. In this tutorial, we''ll explore the most commonly used hooks and how they can improve your code.</p><h2>useState</h2><p>The useState hook lets you add state to functional components. Here''s a simple example:</p><pre><code>function Counter() {\n  const [count, setCount] = useState(0);\n  return (\n    <div>\n      <p>You clicked {count} times</p>\n      <button onClick={() => setCount(count + 1)}>\n        Click me\n      </button>\n    </div>\n  );\n}</code></pre><p>This creates a state variable called count, and a function to update it called setCount.</p>',
    '/react-hooks-programming.png',
    1,
    NOW() - INTERVAL '10 days',
    NOW() - INTERVAL '10 days'
);

INSERT INTO blog_post_tags (blog_post_id, tag) VALUES (1, 'React');
INSERT INTO blog_post_tags (blog_post_id, tag) VALUES (1, 'JavaScript');
INSERT INTO blog_post_tags (blog_post_id, tag) VALUES (1, 'Web Development');

INSERT INTO blog_posts (title, excerpt, content, cover_image, author_id, published_at, created_at)
VALUES (
    'Building a REST API with Node.js and Express',
    'A comprehensive guide to creating a RESTful API using Node.js and Express.',
    '<p>In this tutorial, we''ll build a complete REST API using Node.js and Express. We''ll cover routing, middleware, error handling, and connecting to a database.</p><h2>Setting Up the Project</h2><p>First, let''s initialize a new Node.js project and install the necessary dependencies:</p><pre><code>npm init -y\nnpm install express mongoose cors dotenv</code></pre><p>Now, let''s create our server.js file:</p><pre><code>const express = require(''express'');\nconst cors = require(''cors'');\nconst app = express();\n\napp.use(cors());\napp.use(express.json());\n\napp.listen(5000, () => {\n  console.log(''Server is running on port 5000'');\n});</code></pre>',
    '/nodejs-express-api.png',
    1,
    NOW() - INTERVAL '5 days',
    NOW() - INTERVAL '5 days'
);

INSERT INTO blog_post_tags (blog_post_id, tag) VALUES (2, 'Node.js');
INSERT INTO blog_post_tags (blog_post_id, tag) VALUES (2, 'Express');
INSERT INTO blog_post_tags (blog_post_id, tag) VALUES (2, 'API');
INSERT INTO blog_post_tags (blog_post_id, tag) VALUES (2, 'Backend');

-- Insert sample portfolio items
INSERT INTO portfolio_items (title, description, content, image, user_id, created_at)
VALUES (
    'E-commerce Platform',
    'A full-featured e-commerce platform built with React, Node.js, and MongoDB.',
    '<p>This e-commerce platform provides a complete solution for online stores. It includes features like user authentication, product management, shopping cart functionality, and secure payment processing.</p><p>The frontend is built with React and Redux, providing a responsive and interactive user interface. The backend uses Node.js and Express, with MongoDB as the database.</p>',
    '/placeholder.svg?height=600&width=1200&query=ecommerce website',
    1,
    NOW() - INTERVAL '15 days'
);

INSERT INTO portfolio_item_technologies (portfolio_item_id, technology) VALUES (1, 'React');
INSERT INTO portfolio_item_technologies (portfolio_item_id, technology) VALUES (1, 'Node.js');
INSERT INTO portfolio_item_technologies (portfolio_item_id, technology) VALUES (1, 'Express');
INSERT INTO portfolio_item_technologies (portfolio_item_id, technology) VALUES (1, 'MongoDB');
INSERT INTO portfolio_item_technologies (portfolio_item_id, technology) VALUES (1, 'Redux');

INSERT INTO portfolio_item_features (portfolio_item_id, feature) VALUES (1, 'User authentication and authorization');
INSERT INTO portfolio_item_features (portfolio_item_id, feature) VALUES (1, 'Product catalog with search and filtering');
INSERT INTO portfolio_item_features (portfolio_item_id, feature) VALUES (1, 'Shopping cart and checkout process');
INSERT INTO portfolio_item_features (portfolio_item_id, feature) VALUES (1, 'Payment integration with Stripe');
INSERT INTO portfolio_item_features (portfolio_item_id, feature) VALUES (1, 'Admin dashboard for product management');

INSERT INTO portfolio_items (title, description, content, image, user_id, created_at)
VALUES (
    'Task Management App',
    'A collaborative task management application with real-time updates.',
    '<p>This task management application helps teams collaborate effectively by providing a centralized platform for tracking tasks and projects. It features real-time updates, ensuring that all team members are always on the same page.</p><p>The app is built with React and Firebase, allowing for real-time data synchronization. Tailwind CSS is used for styling, providing a clean and responsive interface.</p>',
    '/placeholder.svg?height=600&width=1200&query=task management app',
    1,
    NOW() - INTERVAL '8 days'
);

INSERT INTO portfolio_item_technologies (portfolio_item_id, technology) VALUES (2, 'React');
INSERT INTO portfolio_item_technologies (portfolio_item_id, technology) VALUES (2, 'Firebase');
INSERT INTO portfolio_item_technologies (portfolio_item_id, technology) VALUES (2, 'Tailwind CSS');
INSERT INTO portfolio_item_technologies (portfolio_item_id, technology) VALUES (2, 'Context API');

INSERT INTO portfolio_item_features (portfolio_item_id, feature) VALUES (2, 'User authentication');
INSERT INTO portfolio_item_features (portfolio_item_id, feature) VALUES (2, 'Create, update, and delete tasks');
INSERT INTO portfolio_item_features (portfolio_item_id, feature) VALUES (2, 'Assign tasks to team members');
INSERT INTO portfolio_item_features (portfolio_item_id, feature) VALUES (2, 'Real-time updates with Firebase');
INSERT INTO portfolio_item_features (portfolio_item_id, feature) VALUES (2, 'Drag-and-drop interface');

-- Insert sample comments
INSERT INTO comments (content, blog_post_id, user_id, created_at)
VALUES (
    'Great article! I''ve been using React Hooks for a while now and they''ve really simplified my code.',
    1,
    2,
    NOW() - INTERVAL '9 days'
);

INSERT INTO comments (content, blog_post_id, user_id, created_at)
VALUES (
    'Thanks for the clear explanation. I was struggling with useEffect but this helped me understand it better.',
    1,
    1,
    NOW() - INTERVAL '8 days'
);

INSERT INTO comments (content, blog_post_id, user_id, created_at)
VALUES (
    'I''ve been looking for a good tutorial on building REST APIs with Node.js. This is exactly what I needed!',
    2,
    2,
    NOW() - INTERVAL '4 days'
);
